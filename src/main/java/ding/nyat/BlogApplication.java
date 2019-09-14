package ding.nyat;

import com.ckfinder.connector.ConnectorServlet;
import com.zaxxer.hikari.HikariDataSource;
import ding.nyat.config.CKFinderConfig;
import ding.nyat.security.*;
import ding.nyat.util.PasswordUtils;
import freemarker.ext.jsp.TaglibFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.CacheControl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BlogApplication extends WebSecurityConfigurerAdapter {

    public BlogApplication(FreeMarkerConfigurer freeMarkerConfigurer, HikariDataSource dataSource) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(Collections.singletonList("/META-INF/security.tld"));
        TaglibFactory taglibFactory = freeMarkerConfigurer.getTaglibFactory();
        taglibFactory.setObjectWrapper(freeMarkerConfigurer.getConfiguration().getObjectWrapper());

        try {
            for (Field field : Role.class.getFields()) {
                if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                    Role role = (Role) field.get(Role.class.newInstance());
                    PreparedStatement preparedStatement =
                            dataSource.getConnection().prepareStatement("INSERT INTO role VALUES (?, ?);");
                    preparedStatement.setInt(1, role.getId());
                    preparedStatement.setString(2, role.getName());
                    preparedStatement.executeQuery();
                }
            }
        } catch (Exception e) {
            if (e.toString().contains("duplicate") || e.toString().contains("unique")) // ko thay class exeption
                System.out.println("Initialize roles: already existed!");
            else System.out.println("Initialize roles: Something went wrong! Can't create roles.");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Autowired
    @Qualifier(value = "CustomUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier(value = "HibernateRepository")
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private Environment env;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PasswordUtils.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers(CKFinderConfig.CKFINDER_URL_PATTERN);

        http.authorizeRequests()
                .antMatchers("/login", "/logout").permitAll()
                .antMatchers("/admin/**").hasRole(Role.ADMIN.getName())
                .antMatchers("/workspace/**", "/user/**").hasAnyRole(Role.AUTHOR.getName(), Role.ADMIN.getName())
                .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

        http.authorizeRequests()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(new CustomUrlAuthenticationFailureHandler())
                .successHandler(new CustomUrlAuthenticationSuccessHandler())
                .and()
                .rememberMe().key("remember-me").tokenValiditySeconds(Integer.parseInt(env.getRequiredProperty("authentication.persistent-login.max-age")))
                .rememberMeServices(new CustomPersistentTokenBasedRememberMeServices("remember-me", userDetailsService, persistentTokenRepository))
                .and()
                /*TODO bat csrf thi /logout phai dung method POST neu de dung logoutUrl(...), hoac ep dung GET nhu cach duoi*/
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessHandler(new CustomUrlLogoutSuccessHanlder())
                .deleteCookies(env.getProperty("server.servlet.session.cookie.name")).invalidateHttpSession(true);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // default value
                //.invalidSessionUrl("/invalid-session")
                .maximumSessions(Integer.parseInt(env.getRequiredProperty("session.max-session")))
                .expiredUrl("/expired-session")
                .and().sessionFixation().migrateSession();// default value // prevent session fixation acttack. // When user tries to authenticate again, old session is invalidated and the attributes from the old session are copied over new one
    }

    /**
     * This is a trick how to inject authenticationManagerBean, userDetailsService
     * to static variables in AdvancedSecurityContextHolder
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AdvancedSecurityContextHolder.setAuthenticationManager(super.authenticationManagerBean());
        AdvancedSecurityContextHolder.setUserDetailsService(this.userDetailsService);
        return super.authenticationManagerBean();
    }

    @Bean
    public ServletRegistrationBean<ConnectorServlet> connectorServlet() {
        ServletRegistrationBean<ConnectorServlet> registrationBean = new ServletRegistrationBean<>();
        registrationBean.setServlet(new ConnectorServlet());
        registrationBean.addUrlMappings(CKFinderConfig.CKFINDER_URL_PATTERN);
        registrationBean.addInitParameter("configuration", CKFinderConfig.class.getTypeName());
        registrationBean.addInitParameter("baseUrl", env.getRequiredProperty("server.base-url"));
        return registrationBean;
    }

    @EnableWebMvc // khong impl tren BlogApplication duoc (loi taglib freemarker)
    public static class CustomWebMvcConfigurer implements WebMvcConfigurer {

        @Bean
        public FilterRegistrationBean<ResourceUrlEncodingFilter> resourceUrlEncodingFilter() {
            FilterRegistrationBean<ResourceUrlEncodingFilter> registrationBean = new FilterRegistrationBean<>();
            registrationBean.setFilter(new ResourceUrlEncodingFilter());
            registrationBean.addUrlPatterns("/*");
            return registrationBean;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
                    .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                    .resourceChain(false)
                    .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                    .addTransformer(new CssLinkResourceTransformer());
        }

    }

}

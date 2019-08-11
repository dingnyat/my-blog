package ding.nyat;

import ding.nyat.security.*;
import ding.nyat.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletContext;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Environment env;

    private ServletContext servletContext;

    @Autowired
    public WebSecurityConfig(Environment env, ServletContext servletContext) {
        this.env = env;
        this.servletContext = servletContext;

        servletContext.getSessionCookieConfig().setHttpOnly(Boolean.parseBoolean(env.getProperty("session.cookie.http-only")));
        servletContext.getSessionCookieConfig().setSecure(Boolean.parseBoolean(env.getProperty("session.cookie.secure")));
        servletContext.getSessionCookieConfig().setMaxAge(Integer.parseInt(env.getProperty("session.cookie.max-age")));
        servletContext.getSessionCookieConfig().setName(env.getProperty("session.cookie.name"));
    }

    @Autowired
    @Qualifier(value = "CustomUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier(value = "HibernateRepository")
    private PersistentTokenRepository persistentTokenRepository;

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeService() {
        return new CustomPersistentTokenBasedRememberMeServices("remember-me", userDetailsService, persistentTokenRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PasswordUtils.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/login", "/logout").permitAll()
                .antMatchers("/admin/**", "/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                .antMatchers("/user/**").hasRole(RoleEnum.AUTHOR.getName())
                .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

        http.authorizeRequests().and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(new CustomUrlAuthenticationFailureHandler()).successHandler(new CustomUrlAuthenticationSuccessHandler())
                .and().rememberMe().key("remember-me").rememberMeServices(this.rememberMeService())
                .tokenValiditySeconds(Integer.parseInt(env.getProperty("authentication.persistent-login.max-age")))
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(new CustomUrlLogoutSuccessHanlder())
                .deleteCookies(env.getProperty("session.cookie.name")).invalidateHttpSession(true);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // default value
                // .invalidSessionUrl("/invalid-session")
                .maximumSessions(Integer.parseInt(env.getProperty("session.max-session"))) // when set value 1, user creates a new session then old one become be invalid
                .expiredUrl("/expired-session")
                .and().sessionFixation().migrateSession();// default value // prevent session fixation acttack. // When user tries to authenticate again, old session is invalidated and the attributes from the old session are copied over new one
    }
}

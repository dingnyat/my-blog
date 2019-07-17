package dou.ding.nyat.blog;

import dou.ding.nyat.blog.security.CustomLogoutSuccessHanlder;
import dou.ding.nyat.blog.security.CustomPersistentTokenBasedRememberMeService;
import dou.ding.nyat.blog.security.RoleEnum;
import dou.ding.nyat.blog.security.UrlAuthenSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@SpringBootApplication
public class BlogApplication extends WebSecurityConfigurerAdapter {

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
    private CustomLogoutSuccessHanlder logoutSuccessHanlder;

    @Autowired
    private UrlAuthenSuccessHandler urlAuthenSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeService() {
        return new CustomPersistentTokenBasedRememberMeService("remember-me", userDetailsService, persistentTokenRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/login", "/logout").permitAll()
                .antMatchers("/admin/**", "/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                .antMatchers("/user/**").hasRole(RoleEnum.AUTHOR.getName())
                .and().exceptionHandling().accessDeniedPage("/access-denied");

        http.authorizeRequests().and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureUrl("/login?error=true").successHandler(this.urlAuthenSuccessHandler)
                .and().rememberMe().key("remember-me").rememberMeServices(this.rememberMeService()).tokenValiditySeconds(60 * 60 * 24 * 7)
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(this.logoutSuccessHanlder)
                .deleteCookies("JSESSIONID", "remember-me").invalidateHttpSession(true);
    }
}

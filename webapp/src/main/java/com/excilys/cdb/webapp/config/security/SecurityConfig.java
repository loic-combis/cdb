package com.excilys.cdb.webapp.config.security;

import static com.excilys.cdb.core.User.MANAGER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import com.excilys.cdb.persistence.dao.UserDAO;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/computers/add", "/computers/delete", "/computers/**/edit").hasAuthority(MANAGER)
                .antMatchers("/computers").authenticated().antMatchers("/", "/home").permitAll().and().formLogin()
                .loginPage("/home").loginProcessingUrl("/authenticate").usernameParameter("login")
                .passwordParameter("password").successHandler(authenticationHandler())
                .failureHandler(authenticationHandler()).and().logout().logoutUrl("/logout").logoutSuccessUrl("/home");
    }

    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }

    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter(CustomUserDetailsService userDetailsService) {
        DigestAuthenticationFilter authFilter = new DigestAuthenticationFilter();
        authFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
        authFilter.setUserDetailsService(userDetailsService);
        return authFilter;
    }

    @Bean
    public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
        DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
        entryPoint.setNonceValiditySeconds(3600);
        entryPoint.setKey("This is my key for digest authentication.");
        entryPoint.setRealmName("This is the realm name");
        return entryPoint;
    }

    @Bean
    public CustomUserDetailsService userDetailsService(UserDAO userDAO) {
        return new CustomUserDetailsService(userDAO);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

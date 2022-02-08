package com.example.movierama;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select u.name as username, u.password, true as enabled from users u where name = ?")
                .authoritiesByUsernameQuery("select u.name as username, 'REGISTERED_USER' as authority from users u where u.name = ?")
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/movies").permitAll()
                .antMatchers("/movies/submit").authenticated()
                .antMatchers("/movies/hate").authenticated()
                .antMatchers("/movies/like").authenticated()
                .antMatchers("/h2-console/**").permitAll()
                .and().csrf().disable().cors().disable()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/movies");

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder;
    }
}

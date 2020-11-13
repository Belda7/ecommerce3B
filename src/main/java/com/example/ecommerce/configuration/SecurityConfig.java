package com.example.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;
    private static final String USERS_QUERY = "SELECT username, password, enabled FROM users WHERE username = ?";
    private static final String AUTHORITIES_QUERY = "SELECT username, role from authorities where username = ?";

    public SecurityConfig (DataSource dataSource){
        this.dataSource = dataSource;
    }

    //Enable JDBC authentication
    @Autowired
    public void configAuthentication (AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY);
    }


    public void configure (WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/h2-console/**");
    }


    public void configure (HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/templates/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/create").permitAll()
                    .antMatchers("/contact-us").permitAll()
                    .antMatchers("/companies").permitAll()
                    .antMatchers("/categories").permitAll()
                    .antMatchers("/products/**").permitAll()
                    .antMatchers("/listusers").hasRole("ADMIN")
                    .antMatchers("/listar").hasRole("ADMIN")
                    .antMatchers("/categories/**").hasRole("ADMIN")
                    .antMatchers("/companies/**").hasAnyRole("SELLER", "ADMIN")
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/index")
                    .and()
                .httpBasic()
                    .and()
                .rememberMe()
                    .tokenValiditySeconds(2419200)
                    .key("ecommerce3b")
                    .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/index").permitAll();

        http
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }

}

package com.mohsintech.supplement_app.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/*
 This is our self-defined security configuration. Instead of using Spring Securities basic config we've defined our own.
 The basic process of validating a request goes like this:

 Request --> Filters --> AuthenticationManager --> AuthenticationProvider --> UserDetailsService --> Database (retrive user info)
               ^-- Security Context is stored.

Once a request goes through the process defined above it goes back the way it came and hits the filter again, then the user which is now a PRINCIPAL
containing identity,authStatus, and authorizations is stored within the security context.
 */

@Configuration
@EnableWebSecurity //this annotation tells Spring that we'll be creating our own security instead of defaults
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    //We declare this manager bean here because we need to be able to use it in our AuthController when a user requests a login
    //The authManager is able to provide an authentication() method that takes user credentials.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
      After the authManager above is used in the AuthController it'll call upon this bean below, the bean below is a AuthProvider
      that actually does the heavy lifting of verifying whether a user exists or not in our database. The process by which it does
      this however is encapsulated by Spring. But essentially it just checks whether the credentials provided by the user match the ones
      we have occurring in our database.
      Since we're retrieving user info from a database we'll implement a DAO(Data access object) provider to retrieve
      user info.
    */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


    /*
        This is the entrypoint for Spring security, every request goes through a chain of filters that are described
        below.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http//must disable csrf for post requests (WILL ADD JWT LATER)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/private/supplement/**").authenticated()
                        .requestMatchers("/api/auth/**","/api/public/supplement/**").permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}

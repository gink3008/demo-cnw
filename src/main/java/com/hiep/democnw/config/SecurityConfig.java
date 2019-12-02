package com.hiep.democnw.config;

import com.hiep.democnw.Dao.Services.RoleService;
import com.hiep.democnw.Entities.real.ControllerEntity;
import com.hiep.democnw.Entities.real.PermissionEntity;
import com.hiep.democnw.Entities.real.RoleControllerEntity;
import com.hiep.democnw.Entities.real.RolesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MapApiRole mapApiRole;
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }
    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public DemoAuthenticationEntryPoint DemoServicesEntryPoint() {
        return new DemoAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/api/**");
        http.authorizeRequests().antMatchers("/user/login**").permitAll();
        http.antMatcher("/api/users/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        HashMap<String, Set<String>> a = mapApiRole.getAllApi();
        a.forEach((key,value) -> {
            System.out.print("\n " + key + " \n");

            try {
                     String[] b = key.split(":",2);
                     b[0] = b[0].toLowerCase();
                     System.out.print("\n " + b[0] + " \n");
                     System.out.print("\n " + value + " \n");
                     System.out.print("\n " + b[1] + " \n");


                    switch (b[0])
                     {
                         case "post":
                             http
                                     .authorizeRequests()
                                     .antMatchers(HttpMethod.POST,b[1]).hasAnyRole(String.join(",",value));
                             break;
                         case "get":
                             http
                                     .authorizeRequests()
                                     .antMatchers(HttpMethod.GET, b[1]).hasAnyRole(String.join(",",value));
                             break;
                         case "delete":
                             http
                                     .authorizeRequests()
                                     .antMatchers(HttpMethod.DELETE, b[1]).hasAnyRole(String.join(",",value));
                             break;
                         case "put":
                             http
                                     .authorizeRequests()
                                     .antMatchers(HttpMethod.PUT, b[1]).hasAnyRole(String.join(",",value));
                             break;
                         default:
                             break;
                     }
                     } catch (Exception e) {
                    e.printStackTrace();
                }
                }
        );

        http
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());


    }
}
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

import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoleService roleService;
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
        // Disable crsf cho đường dẫn /rest/**
//        http.csrf().ignoringAntMatchers("/api/**");
//        http.authorizeRequests().antMatchers("/user/login").permitAll();
//        http.logout()
//                .logoutUrl("/user/logout")
//                .logoutSuccessUrl("http://localhost:3000")
////                .logoutSuccessHandler(logoutSuccessHandler)
////                .invalidateHttpSession(true)
////                .addLogoutHandler(logoutHandler)
////                .deleteCookies(cookieNamesToClear)
//                .and();

        http.csrf().ignoringAntMatchers("/api/**");
        http.authorizeRequests().antMatchers("/user/login**").permitAll();
        http.antMatcher("/api/users/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        http.authorizeRequests(authorizeRequests  -> authorizeRequests .antMatchers(HttpMethod.GET, "/api/users/getlist_user/**")
                .hasRole("ADMIN4")
                                                                        .antMatchers(HttpMethod.GET, "/api/users/getlist_user/**")
                .hasRole("ADMIN2")
        );

        http
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());



        http.authorizeRequests().antMatchers("/api/**").denyAll();
        List<RolesEntity> rolesEntities = roleService.getAllRoles();
        for(RolesEntity rolesEntity: rolesEntities){
            System.out.print(rolesEntity.getName());
        }

        if(rolesEntities != null)
        {
            for(RolesEntity rolesEntity: rolesEntities)
            {
                List<PermissionEntity> permissionEntities =roleService.getPermissionByRole(rolesEntity);
                if(permissionEntities != null)
                {
                    for(PermissionEntity permissionEntity : permissionEntities)
                    {
                        Set<RoleControllerEntity> roleControllers = rolesEntity.getRoleControllersByIdRole();
                        if(roleControllers != null)
                        {
                            for(RoleControllerEntity roleControllerEntity : roleControllers)
                            {
                                List<ControllerEntity> controllerEntities = permissionEntity.getControllersByIdPermission();
                                if(controllerEntities != null)
                                {
                                    for(ControllerEntity controllerEntity : controllerEntities)
                                    {
                                        String controllername1 = controllerEntity.getNameController();
                                        String controllernameEquared = roleControllerEntity.getControllerByIdController().getNameController();

                                        String [] rolename = rolesEntity.getName().split("_", 2); // Lay ROLENAME
                                        System.out.print("\n"+rolename[1] + "\n");
                                        if(controllername1.equals(controllernameEquared)){
                                            System.out.print("--------------------------------------------------------------------------");

                                            System.out.print("\n" +rolesEntity.getName() + ";  " +permissionEntity.getNamePermission() + ":   " + roleControllerEntity.getControllerByIdController().getNameController()+ "\n");
                                            System.out.print(permissionEntity.getUrl()+"/"+controllerEntity.getNameController()+"/**");
                                            switch (controllerEntity.getMethod()) {
                                                case "post":

                                                    System.out.print("\n post \n");
                                                    http
                                                            .authorizeRequests()
                                                            .antMatchers(HttpMethod.POST, permissionEntity.getUrl()+"/"+controllerEntity.getNameController()+"/**").hasRole(rolename[1]);
                                                    break;
                                                case "get":
                                                    System.out.print("\n get \n");
                                                    http
                                                            .authorizeRequests()
                                                            .antMatchers(HttpMethod.GET, permissionEntity.getUrl()+"/"+controllerEntity.getNameController()+"/**").hasRole(rolename[1]);
                                                    break;
                                                case "delete":
                                                    System.out.print("\n delete \n");
                                                    http
                                                            .authorizeRequests()
                                                            .antMatchers(HttpMethod.DELETE, permissionEntity.getUrl()+"/"+controllerEntity.getNameController()+"/**").hasRole(rolename[1]);
                                                    break;
                                                case "put":
                                                    System.out.print("\n put \n");
                                                    http
                                                            .authorizeRequests()
                                                            .antMatchers(HttpMethod.PUT, permissionEntity.getUrl()+"/"+controllerEntity.getNameController()+"/**").hasRole(rolename[1]);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        http
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

    }
}
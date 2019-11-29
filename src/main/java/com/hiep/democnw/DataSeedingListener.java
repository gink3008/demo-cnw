package com.hiep.democnw;

import com.hiep.democnw.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

public class DataSeedingListener /*implements ApplicationListener<ContextRefreshedEvent> */ {


//        @Autowired
//        private UserService userService;
//        @Autowired
//        private UserRespository userRespository;
//        @Autowired
//        private RoleRepository roleRepository;
//
//        @Autowired
//        private PasswordEncoder passwordEncoder;
//
//
//        @Override
//        public void onApplicationEvent(ContextRefreshedEvent arg0) {
//        // Roles
//            if (roleRepository.findByName("ROLE_ADMIN") == null) {
//                roleRepository.save(new Roles("ROLE_ADMIN"));
//            }
//
//            if (roleRepository.findByName("ROLE_MEMBER") == null) {
//                roleRepository.save(new Roles("ROLE_MEMBER"));
//            }
//
//            // Admin account
//            if (userService.findByUsername("admin") == null) {
//                Users admin = new Users();
//                admin.setUsername("admin");
//                admin.setPassword(passwordEncoder.encode("123456"));
//                HashSet<Roles> roles = new HashSet<>();
//                roles.add(roleRepository.findByName("ROLE_ADMIN"));
//                roles.add(roleRepository.findByName("ROLE_MEMBER"));
//                admin.setRoles(roles);
//                userRespository.save(admin);
//            }
//
//            // Member account
//            if (userService.findByUsername("member@gmail.com") == null) {
//                Users user = new Users();
//                user.setUsername("member");
//                user.setPassword(passwordEncoder.encode("123456"));
//                HashSet<Roles> roles = new HashSet<>();
//                roles.add(roleRepository.findByName("ROLE_MEMBER"));
//                user.setRoles(roles);
//                userRespository.save(user);
//            }
//        }

}


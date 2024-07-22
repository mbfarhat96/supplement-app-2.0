package com.mohsintech.supplement_app.controller;

import com.mohsintech.supplement_app.dto.RegisterDto;
import com.mohsintech.supplement_app.model.Role;
import com.mohsintech.supplement_app.model.UserEntity;
import com.mohsintech.supplement_app.repository.RoleRepository;
import com.mohsintech.supplement_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    //user registration mapping
    //the roles for a user entity class must be a list, hence why we're retrieving the object first from the repo
    //then mapping it to the user entity object
    @PostMapping("register")
    public ResponseEntity<String> register( @RequestBody RegisterDto registerDto){
        Role role = roleRepository.findByName("USER").get();
        UserEntity user = UserEntity.builder()
                .username(registerDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(registerDto.getPassword()))
                .roles(Collections.singletonList(role))
                .build();
        userRepository.save(user);

        return new ResponseEntity<>("User Registered Successfully!", HttpStatus.OK);
    }
}

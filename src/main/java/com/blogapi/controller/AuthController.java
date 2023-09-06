package com.blogapi.controller;

import com.blogapi.entity.Role;
import com.blogapi.entity.User;
import com.blogapi.payload.LoginDto;
import com.blogapi.payload.SignupDto;
import com.blogapi.repository.RoleRepository;
import com.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("User SignedIn Successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUer(@RequestBody SignupDto signupDto){
        if(userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email Already Exists", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(signupDto.getUsername())){
            return new ResponseEntity<>("Username Already Exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
    }
}

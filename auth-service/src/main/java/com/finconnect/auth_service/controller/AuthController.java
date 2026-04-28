package com.finconnect.auth_service.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finconnect.auth_service.dto.SignInRequest;
import com.finconnect.auth_service.dto.SignUpRequest;
import com.finconnect.auth_service.entity.Role;
import com.finconnect.auth_service.entity.Users;
import com.finconnect.auth_service.repository.UsersRepository;
import com.finconnect.auth_service.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/signin")
    public String authenticateUser(@RequestBody SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody SignUpRequest request) throws BadRequestException {
        if(usersRepository.findByEmail(request.email()).isPresent()) return "User alredy exists";

        Users newUser = new Users();
        newUser.setFullName(request.fullName());
        newUser.setCpf(request.cpf());
        newUser.setEmail(request.email());
        newUser.setPassword(encoder.encode(request.password()));
        newUser.setRoles(new Role[] { Role.ROLE_USER });

        usersRepository.save(newUser);

        return "User registered successfully";
    }
}

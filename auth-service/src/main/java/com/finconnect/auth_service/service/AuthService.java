package com.finconnect.auth_service.service;

import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.finconnect.auth_service.dto.AccountResponse;
import com.finconnect.auth_service.dto.CreateAccount;
import com.finconnect.auth_service.dto.SignInRequest;
import com.finconnect.auth_service.dto.SignUpRequest;
import com.finconnect.auth_service.entity.Role;
import com.finconnect.auth_service.entity.Users;
import com.finconnect.auth_service.exception.exceptions.DuplicateUserException;
import com.finconnect.auth_service.feign.AccountClient;
import com.finconnect.auth_service.repository.UsersRepository;
import com.finconnect.auth_service.util.JwtUtil;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountClient accountClient;

    //-------------------------//----------------------------//-------------------------------//-----------------------
    public String authenticateUser(SignInRequest request) {
        logger.info("Trying to authenticate user");
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }
    //-------------------------//----------------------------//-------------------------------//-----------------------
    public ResponseEntity<AccountResponse> registerUser(@RequestBody SignUpRequest request) {
        logger.info("Trying to register user");
        if(usersRepository.findByEmail(request.email()).isPresent()) throw new DuplicateUserException("User alredy exists for the email: " + request.email());


        var user = usersRepository.save(createAdminUser(request));

        return accountClient.createAccountOnSignUp(new CreateAccount(user.getId()));
    }
    //-------------------------//----------------------------//-------------------------------//-----------------------
    private Users createAdminUser(SignUpRequest request) {
        var roles = new HashSet<Role>();
        roles.add(Role.ROLE_USER);
        Users newUser = new Users();
        newUser.setFullName(request.fullName());
        newUser.setCpf(request.cpf());
        newUser.setEmail(request.email());
        newUser.setPassword(encoder.encode(request.password()));
        newUser.setRoles(roles);

        return newUser;
    }
    //-------------------------//----------------------------//-------------------------------//-----------------------
}

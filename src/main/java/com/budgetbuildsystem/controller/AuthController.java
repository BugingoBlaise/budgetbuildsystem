package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.dto.LoginRequest;
import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignDto signDto) {
        try{
            userService.signUpUser(signDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch  (Exception ex){
            return new ResponseEntity<>("ERRORS OCCURRED DURING SIGNUP PROCESS", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            String loggedInUser = userDetails.getUsername();

            log.info("logged in user is {}" + loggedInUser);
            return ResponseEntity.ok().body("Logged in successfully as " + role);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}

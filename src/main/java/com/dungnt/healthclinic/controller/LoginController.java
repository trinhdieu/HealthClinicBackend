package com.dungnt.healthclinic.controller;

import com.dungnt.healthclinic.dto.LoginRequest;
import com.dungnt.healthclinic.dto.LoginResponse;
import com.dungnt.healthclinic.model.MyUserDetails;
import com.dungnt.healthclinic.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken((MyUserDetails) authentication.getPrincipal());
        Long userId = ((MyUserDetails) authentication.getPrincipal()).getUser().getId();
        Set<String> roles = ((MyUserDetails) authentication.getPrincipal()).getUser().getRoleNames();
        String role = "USER";
        for (String tmp: roles) {
            if (tmp.equals("ADMIN")) {
                role = "ADMIN";
                break;
            }
            if (tmp.equals("MEDIC")) {
                role = "MEDIC";
            }
        }
        return new LoginResponse(token, userId, role);
    }


}


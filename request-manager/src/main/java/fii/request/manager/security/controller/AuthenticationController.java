package fii.request.manager.security.controller;

import fii.request.manager.domain.Customer;
import fii.request.manager.security.dto.AuthenticationRequestDto;
import fii.request.manager.security.dto.AuthenticationResponseDto;
import fii.request.manager.security.service.CustomerDetailsService;
import fii.request.manager.security.service.JwtTokenService;
import fii.request.manager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;

    private CustomerDetailsService customerDetailsService;


    @Autowired
    AuthenticationController(AuthenticationManager authenticationManager,
                             CustomerDetailsService customerDetailsService,
                             JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.customerDetailsService = customerDetailsService;
    }

    @PostMapping(value="/login")
    @ResponseBody
    AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getEmail(), authenticationRequestDto.getPassword(), new ArrayList<>()));
        UserDetails userDetails = customerDetailsService.loadUserByUsername(authenticationRequestDto.getEmail());
        AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
                .token(jwtTokenService.generateToken(userDetails))
                .build();
        return authenticationResponseDto;
    }
}

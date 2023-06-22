package fii.workflow.manager.security.controller;

import fii.workflow.manager.dto.CustomerDto;
import fii.workflow.manager.mapper.CustomerMapper;
import fii.workflow.manager.security.domain.CustomerDetails;
import fii.workflow.manager.security.dto.AuthenticationRequestDto;
import fii.workflow.manager.security.dto.AuthenticationResponseDto;
import fii.workflow.manager.security.service.CustomerDetailsService;
import fii.workflow.manager.security.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/v1")
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

    @PostMapping(value = "/auth")
    @ResponseBody
    AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getEmail(), authenticationRequestDto.getPassword(), new ArrayList<>()));
        UserDetails userDetails = customerDetailsService.loadUserByUsername(authenticationRequestDto.getEmail());
        AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
                .token(jwtTokenService.generateToken(userDetails))
                .build();
        return authenticationResponseDto;
    }

    @GetMapping(value = "/principal")
    @ResponseBody
    CustomerDto getPrincipal() {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomerDetails customerDetails = (CustomerDetails) customerDetailsService.loadUserByUsername(userName);
        return CustomerMapper.map(customerDetails.getCustomer());
    }
}

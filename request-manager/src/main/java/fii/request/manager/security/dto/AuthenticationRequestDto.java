package fii.request.manager.security.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    String email;
    String password;
}

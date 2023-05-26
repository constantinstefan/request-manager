package fii.request.manager.security.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponseDto {
    String token;
}

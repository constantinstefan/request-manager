package fii.request.manager.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    String oldPassword;
    String newPassword;
}

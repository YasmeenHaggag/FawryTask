package org.example.usermanagementservice.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String username;
    private String code;
    private String newPassword;
}

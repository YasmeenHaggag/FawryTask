package org.example.usermanagementservice.dto;

import lombok.Data;

@Data
//@AllArgsConstructor
public class UserInfoDTO {
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserInfoDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    String role;
}

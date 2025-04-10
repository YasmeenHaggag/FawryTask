package org.example.usermanagementservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @Pattern(
            regexp = "^[23]\\d{13}$",
            message = "Invalid username."
    )
    private String username;
    private String password;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;




    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @Pattern(
            regexp = "^[23]\\d{13}$",
            message = "Invalid National ID format. It must start with 2 or 3 and contain exactly 14 digits."
    ) String getUsername() {
        return username;
    }

    public void setUsername(@Pattern(
            regexp = "^[23]\\d{13}$",
            message = "Invalid National ID format. It must start with 2 or 3 and contain exactly 14 digits."
    ) String username) {
        this.username = username;
    }


}
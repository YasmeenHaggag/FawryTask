package org.example.usermanagementservice.repository;


import org.example.usermanagementservice.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <Users, UUID> {
    Optional<Users> findByUsername(String userName);
    boolean existsByUsername(String username);


}

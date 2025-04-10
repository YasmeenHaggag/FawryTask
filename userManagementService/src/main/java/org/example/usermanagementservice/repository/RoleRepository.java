package org.example.usermanagementservice.repository;

import org.example.usermanagementservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}

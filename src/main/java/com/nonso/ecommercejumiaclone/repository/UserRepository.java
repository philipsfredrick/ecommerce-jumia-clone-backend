package com.nonso.ecommercejumiaclone.repository;

import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
    User  findUserByEmailAndRole(String email, UserRole role);
}

package com.example.backend.repository;

import com.example.backend.model.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
<<<<<<< HEAD

    Optional<UserModel> findByVerificationToken(String verificationToken);

=======
    
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb
}

package com.example.backend.repository;

import com.example.backend.model.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByVerificationToken(String verificationToken);

}
// "accessToken":
// "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2bGFkY2lvcmFwIiwiaWF0IjoxNjgwMjYxNzUxLCJleHAiOjE2ODAyNjIzNTF9.svqaxUG0I2VylIKsGbWWb6rnLOQCfMLSerzKdMYtVuJj04S_rf-RNs5Pf5l3aP_FmH6phxYcBO48D-kLivMflw",
// "refreshToken":
// "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2bGFkY2lvcmFwIiwiaWF0IjoxNjgwMjYxNzUxLCJleHAiOjE2Nzg1NTg3ODR9.PBqS63O3uyiG2DzOjSaVxij7x3WwYsMHn5VPOghWweCAkrYiMbb_r-P2kCAa3K7JJlWV1V5S_L788NA8BBvCsA"
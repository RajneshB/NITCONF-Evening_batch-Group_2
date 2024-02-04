package com.nitconf.backend.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconf.backend.models.User;


public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);
  User findByResetPasswordToken(String token);
  Optional<User> findByEmail(String email);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}

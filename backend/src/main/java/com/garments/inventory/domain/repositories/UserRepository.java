package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    void deleteById(UUID id);
}
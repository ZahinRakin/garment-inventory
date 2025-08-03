package org.example.fabricflowbackend.infrastructure.adapters;

import org.example.fabricflowbackend.Domain.entities.User;
import org.example.fabricflowbackend.Domain.repositories.UserRepository;
import org.example.fabricflowbackend.infrastructure.persistence.UserEntity;
import org.example.fabricflowbackend.infrastructure.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Autowired
    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity;
        
        // For new users during registration, create entity without setting ID
        // This allows Hibernate to generate a new ID automatically
        if (!jpaRepository.existsByEmail(user.getEmail())) {
            // New user - let Hibernate generate ID
            entity = new UserEntity(user, true); // true = isNewEntity, don't set ID
        } else {
            // Existing user - preserve the ID for updates
            entity = new UserEntity(user, false); // false = existing entity
        }
        
        UserEntity savedEntity = jpaRepository.save(entity);
        User savedUser = savedEntity.toDomain();
        
        // For new users, update the domain object with the generated ID
        if (savedEntity.getId() != null && !savedEntity.getId().equals(user.getId())) {
            user.setId(savedEntity.getId());
        }
        
        return savedUser;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserEntity::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
} 
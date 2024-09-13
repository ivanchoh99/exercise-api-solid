package org.examplesolid.infrastructure.db.repository.jpa;

import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CharacterJpaRepository extends JpaRepository<CharacterEntity, UUID> {
    Optional<CharacterEntity> findByName(String name);
}

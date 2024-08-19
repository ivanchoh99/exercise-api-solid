package org.examplesolid.infrastructure.db.repository;

import org.examplesolid.domain.model.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CharacterJpaRepository extends JpaRepository<CharacterEntity, UUID> {
    Optional<CharacterEntity> findByName(String name);
}

package org.examplesolid.domain.abstraction.repository;

import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.naming.NameNotFoundException;
import java.util.List;

@Repository
public interface ICharacterRepository {
    List<CharacterEntity> saveAll(List<CharacterEntity> characterEntities);
    CharacterEntity save(CharacterEntity character);
    Page<CharacterEntity> findAll(Pageable pageable);
    CharacterEntity findByName(String name) throws NameNotFoundException;
}

package org.examplesolid.domain.port.repository;

import org.examplesolid.domain.model.entity.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.naming.NameNotFoundException;
import java.util.List;

@Repository
public interface ICharacterRepository {
    List<CharacterEntity> saveAll(List<CharacterEntity> characters);
    CharacterEntity save(CharacterEntity character);
    Page<CharacterEntity> findAll(Pageable pageable);
    List<CharacterEntity> findAll();

    CharacterEntity findByName(String name) throws NameNotFoundException;
}

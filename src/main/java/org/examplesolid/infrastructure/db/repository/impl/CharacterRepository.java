package org.examplesolid.infrastructure.db.repository.impl;

import jakarta.transaction.Transactional;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.domain.port.repository.ICharacterRepository;
import org.examplesolid.infrastructure.db.repository.CharacterJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.naming.NameNotFoundException;
import java.util.List;

@Repository
public class CharacterRepository implements ICharacterRepository {

    private final CharacterJpaRepository repository;

    @Autowired
    public CharacterRepository(CharacterJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<CharacterEntity> saveAll(List<CharacterEntity> characters) {
        return repository.saveAll(characters);
    }

    @Override
    @Transactional
    public CharacterEntity save(CharacterEntity character) {
        return repository.save(character);
    }

    @Override
    @Transactional
    public Page<CharacterEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public CharacterEntity findByName(String name) throws NameNotFoundException {
        return repository.findByName(name).orElseThrow(() -> new NameNotFoundException("character with name" + name + "not found"));
    }
}

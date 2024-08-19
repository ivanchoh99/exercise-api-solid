package org.examplesolid.infrastructure.db.repository.impl;

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
public class CharacterDBImpl implements ICharacterRepository {

    private final CharacterJpaRepository repository;

    @Autowired
    public CharacterDBImpl(CharacterJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CharacterEntity> saveAll(List<CharacterEntity> characters) {
        return repository.saveAll(characters);
    }

    @Override
    public CharacterEntity save(CharacterEntity character) {
        return repository.save(character);
    }

    @Override
    public Page<CharacterEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<CharacterEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public CharacterEntity findByName(String name) throws NameNotFoundException {
        return repository.findByName(name).orElseThrow(() -> new NameNotFoundException("character with name" + name + "not found"));
    }
}

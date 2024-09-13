package org.examplesolid.infrastructure.db.repository;

import org.examplesolid.domain.abstraction.repository.ICharacterRepository;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.repository.jpa.CharacterJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        List<CharacterEntity> persisted = repository.saveAll(characters);
        repository.flush();
        return persisted;
    }

    @Override
    @Transactional
    public CharacterEntity save(CharacterEntity character) {
        CharacterEntity persisted = repository.save(character);
        repository.flush();
        return persisted;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CharacterEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterEntity findByName(String name) throws NameNotFoundException {
        return repository.findByName(name).orElseThrow(() -> new NameNotFoundException("character with name " + name + " not found"));
    }
}

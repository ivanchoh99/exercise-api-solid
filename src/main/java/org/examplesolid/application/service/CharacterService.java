package org.examplesolid.application.service;

import lombok.extern.slf4j.Slf4j;
import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.api.CharacterResponse;
import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterBaseInformation;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.domain.port.api.IRickAndMortyAPI;
import org.examplesolid.domain.port.mapper.ICharacterMapper;
import org.examplesolid.domain.port.repository.ICharacterRepository;
import org.examplesolid.domain.port.service.ICharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class CharacterService implements ICharacter {

    private final IRickAndMortyAPI clientAPI;
    private final ICharacterRepository characterRepository;
    private final ICharacterMapper mapper;

    @Autowired
    public CharacterService(IRickAndMortyAPI clientAPI, ICharacterRepository characterRepository, ICharacterMapper mapper) {
        this.clientAPI = clientAPI;
        this.characterRepository = characterRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CharacterBaseInformation> getCharactersFromApiAndSort() {
        CharacterResponse response = clientAPI.getRickAndMortyCharacters();
        if (response.results().isEmpty()) return Collections.emptyList();
        return response.results().stream().map(mapper::apiToSimple).sorted(Comparator.comparing(CharacterBaseInformation::getName)).toList();
    }

    @Override
    public List<Character> getAllCharactersFromDB(Pageable pageable) {
        Page<CharacterEntity> entities = characterRepository.findAll(pageable);
        return entities.stream().map(mapper::entityToDomain).toList();
    }

    @Override
    public Character getCharacterFromDB(String name) throws NameNotFoundException {
        CharacterEntity entity = characterRepository.findByName(name);
        return mapper.entityToDomain(entity);
    }

    @Override
    public void saveCharactersFromApi() {
        List<CharacterAPI> characterAPIs = clientAPI.getRickAndMortyCharacters().results();
        List<CharacterEntity> characterEntities = characterAPIs.stream().map(mapper::apiToEntity).toList();
        characterRepository.saveAll(characterEntities);
    }

    @Override
    public Character addFunFact(String nameCharacter, String funFact) throws NameNotFoundException {
        CharacterEntity entity = characterRepository.findByName(nameCharacter);
        entity.addFunFact(funFact);
        CharacterEntity persisted = characterRepository.save(entity);
        return mapper.entityToDomain(persisted);
    }
}
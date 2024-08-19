package org.examplesolid.application.service;

import lombok.extern.slf4j.Slf4j;
import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.api.CharacterResponse;
import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.domain.port.api.IRickAndMortyAPI;
import org.examplesolid.domain.port.mapper.ICharacterMapper;
import org.examplesolid.domain.port.repository.ICharacterRepository;
import org.examplesolid.domain.port.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class CharacterService implements ICharacterService {

    private final IRickAndMortyAPI clientAPI;
    private final ICharacterRepository repository;
    private final ICharacterMapper characterMapper;

    @Autowired
    public CharacterService(IRickAndMortyAPI clientAPI, ICharacterRepository repository, ICharacterMapper characterMapper) {
        this.clientAPI = clientAPI;
        this.repository = repository;
        this.characterMapper = characterMapper;
    }

    @Override
    public List<CharacterSimple> getCharactersFromApiAndSort() {
        CharacterResponse response = clientAPI.getRickAndMortyCharacters();
        if (response.results().isEmpty()) return Collections.emptyList();
        return response.results().stream().map(characterMapper::apiToSimple).sorted(Comparator.comparing(CharacterSimple::getName)).toList();
    }

    @Override
    public List<Character> getAllCharactersFromDBInPage(Pageable pageable) {
        return repository.findAll(pageable).stream().map(characterMapper::entityToDomain).toList();
    }

    @Override
    public List<CharacterEntity> getAllCharactersFromDB() {
        return repository.findAll();
    }

    @Override
    public Character getCharacterFromDB(String name) {
        return characterMapper.entityToDomain(repository.findByName(name));
    }

    @Override
    public List<CharacterEntity> saveCharactersFromApi() {
        List<CharacterAPI> characterAPIs = clientAPI.getRickAndMortyCharacters().results();
        List<CharacterEntity> characterEntities = characterAPIs.stream().map(characterMapper::apiToEntity).toList();
        return repository.saveAll(characterEntities);
    }
}
package org.examplesolid.application.service;

import lombok.extern.slf4j.Slf4j;
import org.examplesolid.application.api.IRickAndMortyAPI;
import org.examplesolid.application.mapper.CharacterMapper;
import org.examplesolid.domain.abstraction.repository.ICharacterRepository;
import org.examplesolid.domain.abstraction.service.ICharacter;
import org.examplesolid.domain.model.Character;
import org.examplesolid.infrastructure.api.model.ApiResponse;
import org.examplesolid.infrastructure.api.model.CharacterApi;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.endpoints.dto.response.CharacterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class CharacterService implements ICharacter {

    private final IRickAndMortyAPI clientAPI;
    private final ICharacterRepository characterRepository;
    private final CharacterMapper mapper;

    @Autowired
    public CharacterService(IRickAndMortyAPI clientAPI, ICharacterRepository characterRepository, CharacterMapper mapper) {
        this.clientAPI = clientAPI;
        this.characterRepository = characterRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CharacterResponse> getSortedCharacters(Pageable pageable) {
        Page<CharacterEntity> entities = characterRepository.findAll(pageable);
        if (!entities.isEmpty()) return this.sortCharacters(entities.stream().map(mapper::entityToResponse));
        List<CharacterApi> characterApis = this.getCharacterFromApi();
        List<CharacterEntity> charactersFromDb = this.persistCharactersApi(characterApis);
        return this.sortCharacters(charactersFromDb.stream().map(mapper::entityToResponse));
    }

    private List<CharacterResponse> sortCharacters(Stream<CharacterResponse> characterResponses) {
        return characterResponses.sorted(Comparator.comparing(CharacterResponse::getName)).toList();
    }

    private List<CharacterApi> getCharacterFromApi() {
        ApiResponse responseApi = clientAPI.getRickAndMortyCharacters();
        return responseApi.results().stream().toList();
    }

    private List<CharacterEntity> persistCharactersApi(List<CharacterApi> characterApis) {
        List<CharacterEntity> toPersist = characterApis.stream().map(mapper::apiToEntity).toList();
        return characterRepository.saveAll(toPersist);
    }

    @Override
    public CharacterResponse addFact(String nameCharacter, String funFact) throws NameNotFoundException {
        CharacterEntity entity = characterRepository.findByName(nameCharacter);
        Character character = mapper.entityToDomain(entity);
        character.addFact(funFact);
        CharacterEntity toPersist = mapper.domainToEntity(character);
        CharacterEntity persisted = characterRepository.save(toPersist);
        return mapper.entityToResponse(persisted);
    }
}
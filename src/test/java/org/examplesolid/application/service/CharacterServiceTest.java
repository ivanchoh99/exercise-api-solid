package org.examplesolid.application.service;

import org.assertj.core.api.Assertions;
import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.api.CharacterResponse;
import org.examplesolid.domain.model.api.Location;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.port.api.IRickAndMortyAPI;
import org.examplesolid.domain.port.mapper.ICharacterMapper;
import org.examplesolid.infrastructure.db.repository.CharacterMySQL;
import org.examplesolid.infrastructure.mapper.CharacterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {
    @Mock
    private static IRickAndMortyAPI rickAndMortyAPI;
    @Mock
    private static CharacterMySQL repository;
    private static CharacterService characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.registerModule(new RecordModule());
        ICharacterMapper mapper = new CharacterMapper(modelMapper);
        characterService = new CharacterService(rickAndMortyAPI, repository, mapper);
    }


    @Test
    void ListShouldSortAlphabetically() {
        //*Arrange
        List<CharacterAPI> characterAPIs = new ArrayList<>();
        Location location = new Location("lugar", "url");
        characterAPIs.add(new CharacterAPI(1, "rick", "live", "human", "hombre", location, location, "image", Collections.emptyList(), "url", "create"));
        characterAPIs.add(new CharacterAPI(2, "adolfo", "live", "human", "hombre", location, location, "image", Collections.emptyList(), "url", "create"));
        CharacterResponse characterResponse = new CharacterResponse(null, characterAPIs);
        Mockito.when(rickAndMortyAPI.getRickAndMortyCharacters()).thenReturn(characterResponse);
        //*Act
        List<CharacterSimple> characterSimples = characterService.getCharactersFromApiAndSort();
        //*Assert
        Assertions.assertThat(characterSimples.get(0).getName()).isEqualTo("adolfo");
    }

    @Test
    void ListShouldReturnEmptyCollectionWhenDataNotFound() {
        //*Arrange
        List<CharacterAPI> characters = new ArrayList<>();
        CharacterResponse characterResponseMocks = new CharacterResponse(null, characters);
        Mockito.when(rickAndMortyAPI.getRickAndMortyCharacters()).thenReturn(characterResponseMocks);
        //*Act
        List<CharacterSimple> charactersResult = characterService.getCharactersFromApiAndSort();
        //*Assert
        Assertions.assertThat(charactersResult).isEmpty();
    }
}
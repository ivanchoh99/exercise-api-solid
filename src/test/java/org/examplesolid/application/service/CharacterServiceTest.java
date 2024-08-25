package org.examplesolid.application.service;

import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.api.CharacterResponse;
import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterBaseInformation;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.infrastructure.api.RickAndMortyAPIClient;
import org.examplesolid.infrastructure.db.repository.impl.CharacterDBImpl;
import org.examplesolid.infrastructure.mapper.CharacterMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;

import javax.naming.NameNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.examplesolid.application.util.Constants.SEPARATOR_CONCAT_FACT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    private static RickAndMortyAPIClient api;
    private static CharacterDBImpl repository;
    private static CharacterService service;

//    public CharacterServiceTest() {
//        api = mock(RickAndMortyAPIClient.class);
//        repository = mock(CharacterDBImpl.class);
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.registerModule(new RecordModule());
//        CharacterMapper mapper = new CharacterMapper(modelMapper);
//        service = new CharacterService(api,repository, mapper);
//    }

    @BeforeAll
    static void setUpTestClass() throws Exception {
        MockitoAnnotations.openMocks(CharacterServiceTest.class).close();
        api = mock(RickAndMortyAPIClient.class);
        repository = mock(CharacterDBImpl.class);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.registerModule(new RecordModule());
        CharacterMapper mapper = new CharacterMapper(modelMapper);
        service = new CharacterService(api, repository, mapper);
    }

    @Test
    void givenAFunFactAndNameCharacter_whenAddTheFunFactToTheEntity_thenReturnTheCharacterModify() throws NameNotFoundException {
        //* Arrange
        UUID uuid = UUID.randomUUID();
        String nameCharacter = "Rick Sanchez";
        String funFact1 = "Abuelo";
        String funFact2 = "Cientifico";
        String expectedFunFacts = funFact1 + SEPARATOR_CONCAT_FACT + funFact2;
        CharacterEntity entity = new CharacterEntity(uuid, nameCharacter, funFact1);
        CharacterEntity persistedEntity = new CharacterEntity(uuid, nameCharacter, expectedFunFacts);
        when(repository.findByName(nameCharacter)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(persistedEntity);
        //* Act
        Character result = service.addFunFact(nameCharacter, funFact2);
        //* Assert
        assertThat(result.getFunFacts()).as("Add a fun fact to character correctly").withFailMessage("The fun facts character don't be modify correctly").contains(funFact1, funFact2);
    }

    @Test
    void consumingTheApi_whenGetCharacterResponseFromApi_thenSortByName() {
        //* Arrange
        CharacterAPI characterAPI1 = CharacterAPI.builder().name("Zuñiga").build();
        CharacterAPI characterAPI2 = CharacterAPI.builder().name("Andres").build();
        CharacterResponse response = new CharacterResponse(null, List.of(characterAPI1, characterAPI2));
        when(api.getRickAndMortyCharacters()).thenReturn(response);
        //* Act
        List<CharacterBaseInformation> characterSimples = service.getCharactersFromApiAndSort();
        //* Assert
        assertThat(characterSimples).as("Characters Simples are order alphabetically").withFailMessage("The Characters Simples are not order alphabetically").isSortedAccordingTo(Comparator.comparing(CharacterBaseInformation::getName));
    }

    @Test
    void consumingTheApi_whenGetEmptyCharacterResponseFromApi_thenReturnEmptyList() {
        //* Arrange
        CharacterResponse response = new CharacterResponse(null, List.of());
        when(api.getRickAndMortyCharacters()).thenReturn(response);
        //* Act
        List<CharacterBaseInformation> characterSimples = service.getCharactersFromApiAndSort();
        //* Assert
        assertThat(characterSimples).as("Validate that getCharactersFromApiAndSort can return empty list").withFailMessage("The Characters Simples list is not empty or aren't instance").isEmpty();
    }
}
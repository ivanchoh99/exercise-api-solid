package org.examplesolid.application.service;

import org.examplesolid.application.model.api.ApiResponse;
import org.examplesolid.application.model.api.CharacterApi;
import org.examplesolid.application.model.response.CharacterResponse;
import org.examplesolid.infrastructure.api.RickAndMortyAPIClient;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.repository.CharacterRepository;
import org.examplesolid.infrastructure.mapper.CharacterMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.naming.NameNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    private static RickAndMortyAPIClient api;
    private static CharacterRepository repository;
    private static CharacterService service;


    @BeforeAll
    static void setUpTestClass() throws Exception {
        MockitoAnnotations.openMocks(CharacterServiceTest.class).close();
        api = mock(RickAndMortyAPIClient.class);
        repository = mock(CharacterRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.registerModule(new RecordModule());
        CharacterMapper mapper = new CharacterMapper();
        service = new CharacterService(api, repository, mapper);
    }

    @Test
    void givenAFunFactAndNameCharacter_whenAddTheFunFactToTheEntity_thenReturnTheCharacterModify() throws NameNotFoundException {
        //* Arrange
        UUID uuid = UUID.randomUUID();
        String nameCharacter = "Rick Sanchez";
        String fact1 = "Abuelo";
        String fact2 = "Cientifico";
        CharacterEntity entity = new CharacterEntity();
        entity.setName(nameCharacter);
        entity.setFacts(fact1);
        CharacterEntity persistedEntity = new CharacterEntity();
        persistedEntity.setUuid(UUID.randomUUID());
        persistedEntity.setName(nameCharacter);
        persistedEntity.setFacts(fact1);
        when(repository.findByName(nameCharacter)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(persistedEntity);
        //* Act
        CharacterResponse result = service.addFact(nameCharacter, fact2);
        //* Assert
        assertThat(result.getFacts())
                .as("Add a fun fact to character correctly")
                .withFailMessage("The fun facts character don't be modify correctly")
                .contains(fact1, fact2);
    }

    @Test
    void consumingTheApiOrGetDataFromDb_whenGetCharacters_thenSortByName() {
        //* Arrange
        Pageable pageable = PageRequest.of(0, 100);
        CharacterApi characterApi1 = new CharacterApi();
        characterApi1.setName("Alberto");
        CharacterApi characterApi2 = new CharacterApi();
        characterApi2.setName("Zuñiga");
        ApiResponse response = new ApiResponse(null, List.of(characterApi1, characterApi2));
        when(api.getRickAndMortyCharacters()).thenReturn(response);
        //* Act
        List<CharacterResponse> characterSimples = service.getSortedCharacters(pageable);
        //* Assert
        assertThat(characterSimples)
                .as("Characters Simples are order alphabetically")
                .withFailMessage("The Characters Simples are not order alphabetically")
                .isSortedAccordingTo(Comparator.comparing(CharacterResponse::getName));
    }

    @Test
    void consumingTheApi_whenGetEmptyCharacterResponseFromApi_thenReturnEmptyList() {
        //* Arrange
        Pageable pageable = PageRequest.of(0, 100);
        ApiResponse response = new ApiResponse(null, List.of());
        when(api.getRickAndMortyCharacters()).thenReturn(response);
        //* Act
        List<CharacterResponse> characterSimples = service.getSortedCharacters(pageable);
        //* Assert
        assertThat(characterSimples).as("Validate that getSortedCharacters can return empty list").withFailMessage("The Characters Simples list is not empty or aren't instance").isEmpty();
    }
}
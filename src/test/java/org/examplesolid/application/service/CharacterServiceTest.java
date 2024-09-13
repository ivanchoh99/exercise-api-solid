package org.examplesolid.application.service;

import org.examplesolid.application.model.api.ApiResponse;
import org.examplesolid.application.model.api.CharacterApi;
import org.examplesolid.application.model.api.Location;
import org.examplesolid.application.model.response.CharacterResponse;
import org.examplesolid.infrastructure.api.RickAndMortyAPIClient;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.repository.CharacterRepository;
import org.examplesolid.infrastructure.mapper.CharacterMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.naming.NameNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    private RickAndMortyAPIClient api;
    @Mock
    private CharacterRepository repository;
    @Spy
    private CharacterMapper mapper;
    @InjectMocks
    private CharacterService service;

    //* Arrange Class Test
    private final UUID uuid = UUID.randomUUID();
    private final String nameCharacter1 = "Rick Sanchez";
    private final String nameCharacter2 = "Alberto";

    @Test
    void givenAFunFactAndNameCharacter_whenAddTheFunFactToTheEntity_thenReturnTheCharacterModify() throws NameNotFoundException {
        //* Arrange
        CharacterEntity find = new CharacterEntity();
        CharacterEntity persistedEntity = new CharacterEntity();
        find.setUuid(uuid);
        find.setName(nameCharacter1);
        String fact1 = "Abuelo";
        find.setFacts(fact1);
        persistedEntity.setUuid(uuid);
        persistedEntity.setName(nameCharacter1);
        String fact2 = "Cientifico";
        persistedEntity.setFacts(fact1 + "-" + fact2);
        when(repository.findByName(nameCharacter1)).thenReturn(find);
        when(repository.save(any(CharacterEntity.class))).thenReturn(persistedEntity);
        //* Act
        CharacterResponse result = service.addFact(nameCharacter1, fact2);
        //* Assert
        assertThat(result.getFacts()).as("Add a fun fact to character correctly")
                .withFailMessage("The fun facts character don't be modify correctly")
                .contains(fact1, fact2);
    }

    @Test
    void getCharacters_whenDbIsEmpty_thenConsumeApiAndReturnCharactersSorted() {
        //* Arrange
        Location location = new Location("locationName", "url");
        CharacterApi characterApi1 = new CharacterApi();
        characterApi1.setName(nameCharacter2);
        characterApi1.setEpisode(List.of("https://rickandmortyapi.com/api/episode/1", "https://rickandmortyapi.com/api/episode/2"));
        characterApi1.setLocation(location);
        characterApi1.setOrigin(location);
        CharacterApi characterApi2 = new CharacterApi();
        characterApi2.setName(nameCharacter1);
        characterApi1.setEpisode(List.of("https://rickandmortyapi.com/api/episode/1", "https://rickandmortyapi.com/api/episode/2"));
        characterApi2.setLocation(location);
        characterApi2.setOrigin(location);
        ApiResponse response = new ApiResponse(null, List.of(characterApi1, characterApi2));
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        when(api.getRickAndMortyCharacters()).thenReturn(response);
        //* Act
        List<CharacterResponse> characters = service.getSortedCharacters(Page.empty().getPageable());
        //* Assert
        assertThat(characters)
                .as("Characters are order alphabetically")
                .withFailMessage("The Characters are not order alphabetically")
                .isSortedAccordingTo(Comparator.comparing(CharacterResponse::getName));
    }

    @Test
    void getCharacters_whenDbIsNotEmpty_thenFindInDbAndReturnCharactersSorted() {
        //* Arrange
        Pageable pageable = PageRequest.of(0, 50);
        CharacterEntity character1 = new CharacterEntity();
        character1.setName(nameCharacter1);
        CharacterEntity character2 = new CharacterEntity();
        character2.setName(nameCharacter2);
        Page<CharacterEntity> entities = new PageImpl<>(List.of(character1, character2));
        when(repository.findAll(any(Pageable.class))).thenReturn(entities);
        //* Act
        List<CharacterResponse> characters = service.getSortedCharacters(pageable);
        //* Assert
        assertThat(characters)
                .as("Characters are order alphabetically")
                .withFailMessage("The Characters are not order alphabetically")
                .isSortedAccordingTo(Comparator.comparing(CharacterResponse::getName));

    }
}
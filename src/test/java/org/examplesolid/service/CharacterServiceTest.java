package org.examplesolid.service;

import org.assertj.core.api.Assertions;
import org.examplesolid.client.IRickAndMortyAPI;
import org.examplesolid.model.api.response.CharacterAPI;
import org.examplesolid.model.api.response.CharacterResponse;
import org.examplesolid.model.api.response.Location;
import org.examplesolid.model.dto.CharacterDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    private IRickAndMortyAPI rickAndMortyAPI;

    @InjectMocks
    private CharacterService characterService;

    @DisplayName("test sort characters alphabetically")
    @Test
    void ListShouldSortAlphabetically() {
        //*Arrange
        Location locationGen = new Location("alguna parte", "url");
        List<CharacterAPI> characters = new ArrayList<>();
        characters.add(new CharacterAPI(1,"rick sanchez","Live", "Humano", "Masculino", locationGen, locationGen,null, null, null, null));
        characters.add(new CharacterAPI(2,"abelardo mendez","death", "Pajaro", "Indefinido", locationGen, locationGen,null, null, null, null));
        CharacterResponse characterResponseMocks = new CharacterResponse(null, characters);

        Mockito.when(rickAndMortyAPI.getRickAndMortyCharacters()).thenReturn(Mono.just(characterResponseMocks));

        //*Act
        List<CharacterDTO> charactersResult = characterService.getCharacters();

        //*Assert
        Assertions.assertThat(charactersResult.get(0).getName()).isEqualTo("abelardo mendez");
    }

    @Test
    void ListShouldReturnEmptyCollectionWhenDataNotFound() {
        //*Arrange
        List<CharacterAPI> characters = new ArrayList<>();
        CharacterResponse characterResponseMocks = new CharacterResponse(null, characters);

        Mockito.when(rickAndMortyAPI.getRickAndMortyCharacters()).thenReturn(Mono.just(characterResponseMocks));

        //*Act
        List<CharacterDTO> charactersResult = characterService.getCharacters();

        //*Assert
        Assertions.assertThat(charactersResult).isEmpty();
    }
}
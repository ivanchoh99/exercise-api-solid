package org.examplesolid.services;

import org.examplesolid.client.RickAndMortyAPIClient;
import org.examplesolid.model.dto.CharacterDTO;
import org.examplesolid.model.api.response.CharacterAPI;
import org.examplesolid.model.api.response.Location;
import org.examplesolid.service.CharacterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static reactor.core.publisher.Mono.when;

class CharacterServiceTest {

    @Mock
    private RickAndMortyAPIClient clientAPI;

    @InjectMocks
    private CharacterService characterService;

    @Test
    void orderCharacters() {
        CharacterAPI characterAPI = new CharacterAPI(
                361,
                "Toxic Rick",
                "Dead",
                "Humanoid",
                "Rick's Toxic Side",
                new Location("name", "url"),
                new Location("name", "url"),
                "image",
                new String[]{"episodex"},
                "url",
                "created"
        );
        List<CharacterDTO>
        characterService.orderCharacters()
    }
}
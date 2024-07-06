package org.examplesolid.client;

import org.examplesolid.model.api.response.CharacterResponse;
import reactor.core.publisher.Mono;

public interface IRickAndMortyAPI {
    Mono<CharacterResponse> getRickAndMortyCharacters();
}

package org.examplesolid.client;

import org.examplesolid.model.api.response.CharacterResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Repository
public class RickAndMortyAPIClient implements IRickAndMortyAPI{

    private final WebClient webClient;

    public RickAndMortyAPIClient(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl("https://rickandmortyapi.com/api").build();
    }

    @Override
    public Mono<CharacterResponse> getRickAndMortyCharacters(){
        return webClient.get().uri("/character")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CharacterResponse.class);
    }
}

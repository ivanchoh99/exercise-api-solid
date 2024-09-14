package org.examplesolid.infrastructure.api;

import org.examplesolid.application.api.IRickAndMortyAPI;
import org.examplesolid.application.exception.ErrorClientException;
import org.examplesolid.infrastructure.api.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;


@Repository
public class RickAndMortyAPIClient implements IRickAndMortyAPI {

    private final RestClient restClient;

    @Autowired
    public RickAndMortyAPIClient(RestClient.Builder restClient) {
        this.restClient = restClient.baseUrl("https://rickandmortyapi.com/api").build();
    }

    @Override
    public ApiResponse getRickAndMortyCharacters() {
        return restClient.get()
                .uri("/character")
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ErrorClientException(response.getStatusText());
                })).onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ErrorClientException(response.getStatusText());
                })).body(ApiResponse.class);
    }
}

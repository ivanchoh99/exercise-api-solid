package org.examplesolid.domain.model.api;

import lombok.Builder;

import java.util.List;

@Builder
public record CharacterAPI(int id,
                           String name,
                           String status,
                           String species,
                           String gender,
                           Location origin,
                           Location location,
                           String image,
                           List<String> episode,
                           String url, String created) {
}

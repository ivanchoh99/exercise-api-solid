package org.examplesolid.domain.model.api;

import java.util.List;

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

    public CharacterAPI(String name) {
        this(0, name, null, null, null, null, null, null, null, null, null);
    }
}

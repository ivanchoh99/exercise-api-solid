package org.examplesolid.model.api.response;

public record CharacterAPI(int id,
                           String name,
                           String status,
                           String species,
                           String gender,
                           Location origin,
                           Location location,
                           String image,
                           String[] episode,
                           String url,
                           String created) {

}

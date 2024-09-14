package org.examplesolid.infrastructure.api.model;

import lombok.Data;

import java.util.List;

@Data
public class CharacterApi {
    private int id;
    private String name;
    private String status;
    private String species;
    private String gender;
    private Location origin;
    private Location location;
    private String image;
    private List<String> episode;
    private String url;
    private String create;
}

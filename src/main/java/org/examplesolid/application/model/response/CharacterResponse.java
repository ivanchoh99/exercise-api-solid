package org.examplesolid.application.model.response;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CharacterResponse {
    private String name;
    private String status;
    private String species;
    private String gender;
    private String origin;
    private String location;
    private Set<Integer> episodes;
    private List<String> facts;
}

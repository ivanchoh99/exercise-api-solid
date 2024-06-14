package org.examplesolid.model.dto;

import lombok.Getter;
import org.examplesolid.model.api.response.CharacterAPI;

@Getter
public class CharacterDTO {
    private final String name;
    private final String status;
    private final String species;
    private final String gender;
    private final String nameLocation;

    public CharacterDTO(CharacterAPI characterAPI) {
        this.name = characterAPI.name();
        this.status = characterAPI.status();
        this.species = characterAPI.species();
        this.gender = characterAPI.gender();
        this.nameLocation = characterAPI.location().name();
    }
}

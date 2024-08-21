package org.examplesolid.domain.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterSimple {
    private String name;
    private String status;
    private String species;
    private String gender;
    private String locationName;

    public CharacterSimple(String name) {
        this.name = name;
    }
}

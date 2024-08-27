package org.examplesolid.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CharacterResponse {
    private String name;
    private Set<Integer> episodes;
    private List<String> funFacts;
}

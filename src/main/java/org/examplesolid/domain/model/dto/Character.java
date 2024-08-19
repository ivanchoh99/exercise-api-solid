package org.examplesolid.domain.model.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Character {
    private UUID uuid;
    private String name;
    private Set<Integer> episodes;
    private List<String> funFacts;
}

package org.examplesolid.domain.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Character {
    private String id;
    private String name;
    private String specie;
    private String status;
    private String gender;
    private String origin;
    private String location;
    private Set<Integer> episodes;
    private List<String> facts;

    public void addFact(String newFact) {
        this.facts.add(newFact);
    }
}

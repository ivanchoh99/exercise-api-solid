package org.examplesolid.service;

import org.examplesolid.model.dto.CharacterDTO;

import java.util.List;

public interface ICharacters {
    List<CharacterDTO> getCharacters();

    List<CharacterDTO> orderCharacters(List<CharacterDTO> characters);
}

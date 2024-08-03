package org.examplesolid.domain.port.service;

import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICharacterService {
    List<CharacterSimple> getCharactersFromApiAndSort();
    List<Character> getAllCharactersFromDB(Pageable pageable);
    Character getCharacterFromDB(String name);
    List<CharacterEntity> saveCharactersFromApi();
}

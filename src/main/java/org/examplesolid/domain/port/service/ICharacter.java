package org.examplesolid.domain.port.service;

import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.springframework.data.domain.Pageable;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ICharacter {
    List<CharacterSimple> getCharactersFromApiAndSort();
    List<Character> getAllCharactersFromDBInPage(Pageable pageable);
    List<CharacterEntity> getAllCharactersFromDB();

    Character getCharacterFromDB(String name) throws NameNotFoundException;

    void saveCharactersFromApi();

    Character addFunFact(String nameCharacter, String funFact) throws NameNotFoundException;
}

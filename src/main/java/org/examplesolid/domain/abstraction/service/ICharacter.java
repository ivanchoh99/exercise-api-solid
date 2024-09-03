package org.examplesolid.domain.abstraction.service;

import org.examplesolid.application.model.response.CharacterResponse;
import org.springframework.data.domain.Pageable;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ICharacter {
    List<CharacterResponse> getSortedCharacters(Pageable pageable);

    CharacterResponse addFact(String nameCharacter, String funFact) throws NameNotFoundException;
}

package org.examplesolid.domain.port.mapper;

import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.model.entity.CharacterEntity;

public interface ICharacterMapper {
    Character entityToDomain(CharacterEntity characterEntity);
    CharacterEntity domainToEntity(Character character);
    CharacterSimple apiToSimple(CharacterAPI characterAPI);
    CharacterEntity apiToEntity(CharacterAPI characterAPI);
}

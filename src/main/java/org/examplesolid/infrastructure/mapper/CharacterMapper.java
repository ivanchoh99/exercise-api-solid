package org.examplesolid.infrastructure.mapper;

import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.domain.model.entity.EpisodeEntity;
import org.examplesolid.domain.port.mapper.ICharacterMapper;
import org.examplesolid.domain.port.service.IFunFact;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterMapper implements ICharacterMapper {

    private final ModelMapper mapper;
    private final IFunFact funFactService;

    @Autowired
    public CharacterMapper(ModelMapper mapper, IFunFact funFactService) {
        this.mapper = mapper;
        this.funFactService = funFactService;
    }

    @Override
    public Character entityToDomain(CharacterEntity characterEntity) {
        Set<Integer> episodes = Collections.emptySet();
        if (characterEntity.getEpisodes() != null) {
            episodes = characterEntity.getEpisodes().stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet());
        }
        List<String> funFacts = funFactService.stringToList(characterEntity.getFunFacts());
        return new Character(characterEntity.getName(), episodes, funFacts);
    }

    @Override
    public CharacterEntity domainToEntity(Character character) {
        return mapper.map(character, CharacterEntity.class);
    }

    @Override
    public CharacterSimple apiToSimple(CharacterAPI characterAPI) {
        return mapper.map(characterAPI, CharacterSimple.class);
    }

    @Override
    public CharacterEntity apiToEntity(CharacterAPI characterAPI) {
        Set<EpisodeEntity> episodes = characterAPI.episode().stream().map(episode -> Integer.parseInt(episode.replaceAll("https://rickandmortyapi.com/api/episode/", ""))).map(EpisodeEntity::new).collect(Collectors.toSet());
        CharacterEntity character = mapper.map(characterAPI, CharacterEntity.class);
        episodes.forEach(episode -> episode.setCharacter(character));
        character.setEpisodes(episodes);
        return character;
    }
}

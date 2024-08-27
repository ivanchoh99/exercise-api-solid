package org.examplesolid.infrastructure.mapper;

import org.examplesolid.application.util.StringUtility;
import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.dto.CharacterInfoResponse;
import org.examplesolid.domain.model.dto.CharacterResponse;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.domain.model.entity.EpisodeEntity;
import org.examplesolid.domain.port.mapper.ICharacterMapper;
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

    @Autowired
    public CharacterMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CharacterResponse entityToDomain(CharacterEntity characterEntity) {
        Set<Integer> episodes = characterEntity.getEpisodes() == null ?
                Collections.emptySet() : characterEntity.getEpisodes().stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet());
        List<String> funFacts = StringUtility.stringToList(characterEntity.getFunFacts());
        return new CharacterResponse(characterEntity.getName(), episodes, funFacts);
    }

    @Override
    public CharacterInfoResponse apiToSimple(CharacterAPI characterAPI) {
        return mapper.map(characterAPI, CharacterInfoResponse.class);
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

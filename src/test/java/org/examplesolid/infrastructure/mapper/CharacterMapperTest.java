package org.examplesolid.infrastructure.mapper;

import org.examplesolid.application.model.api.CharacterApi;
import org.examplesolid.application.model.api.Location;
import org.examplesolid.application.model.response.CharacterResponse;
import org.examplesolid.domain.model.Character;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.entity.EpisodeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CharacterMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CharacterMapper mapper;

    private final String name = "Rick Sanchez";
    private final String fact1 = "cientifico";
    private final String fact2 = "loco";
    private final String factsString = fact1 + "-" + fact2;
    private final Set<Integer> episodes = Set.of(1, 2, 3);
    private final List<String> factsList = List.of(fact1, fact2);
    private final CharacterEntity entity = new CharacterEntity();

    @BeforeEach
    void setUp() {
        Set<EpisodeEntity> episodesEntities = episodesOfCharacter.apply(entity);
        entity.setName(name);
        entity.setFacts(factsString);
        entity.setEpisodes(episodesEntities);

    }

    Function<CharacterEntity, Set<EpisodeEntity>> episodesOfCharacter = characterEntity -> episodes.stream().map(number -> {
        EpisodeEntity episode = new EpisodeEntity();
        episode.setNumberEpisode(number);
        episode.setCharacter(characterEntity);
        return episode;
    }).collect(Collectors.toSet());


    @Test
    void entityToResponse() {
        //* Arrange
        CharacterResponse expected = new CharacterResponse();
        expected.setName(name);
        expected.setFacts(List.of(fact1, fact2));
        expected.setEpisodes(episodes);
        //* Act
        CharacterResponse response = mapper.entityToResponse(entity);
        //* Assert
        assertThat(response).as("convert entity a response").withFailMessage("No se convirtio la entidad correctamentes")
                .hasFieldOrPropertyWithValue("name", expected.getName())
                .hasFieldOrPropertyWithValue("facts", expected.getFacts())
                .hasFieldOrPropertyWithValue("episodes", expected.getEpisodes());
    }

    @Test
    void apiToEntity() {
        //* Arrange
        CharacterApi api = new CharacterApi();
        Location location = new Location("location", "url");
        List<String> urlsEpisodes = episodes.stream().map(number -> "https://rickandmortyapi.com/api/episode/" + number).toList();
        api.setName(name);
        api.setLocation(location);
        api.setOrigin(location);
        api.setEpisode(urlsEpisodes);
        //* Act
        CharacterEntity result = mapper.apiToEntity(api);
        //* Assert
        assertThat(result.getName()).as("convert entity to response: Name ").isEqualTo(name);
        assertThat(result.getEpisodes().stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet())).as("convert entity to response: episodes").containsAll(episodes);
    }

    @Test
    void entityToDomain() {
        //* Act
        Character result = mapper.entityToDomain(entity);
        //* Assert
        assertThat(result.getName())
                .as("convert entity to Domain: Name ")
                .isEqualTo(name);
        assertThat(result.getEpisodes())
                .as("convert entity to Domain: episodes are correctly")
                .containsAll(episodes);
        assertThat(result.getFacts())
                .as("convert entity to Domain: facts ")
                .containsAll(factsList);
    }

    @Test
    void domainToEntity() {
        //* Arrange
        Character domain = new Character();
        domain.setId(UUID.randomUUID().toString());
        domain.setName(name);
        domain.setEpisodes(episodes);
        domain.setFacts(factsList);
        //* Act
        CharacterEntity result = mapper.domainToEntity(domain);
        //* Assert
        assertThat(result.getName()).as("convert domain to entity: Name")
                .isEqualTo(name);
        assertThat(result.getEpisodes().stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet()))
                .as("convert domain to entity: episodes")
                .containsAll(episodes);
        assertThat(result.getFacts())
                .as("convert domain to entity: fats")
                .isEqualTo(factsString);

    }
}
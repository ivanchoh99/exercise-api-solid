package org.examplesolid.infrastructure.db.repository.impl;

import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.entity.EpisodeEntity;
import org.examplesolid.infrastructure.db.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataIntegrityViolationException;

import javax.naming.NameNotFoundException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=update",})
class CharacterRepositoryTest {

    @SpyBean
    private CharacterRepository repository;

    @Test
    void givenIncompleteCharacter_whenPersist_thenThrowException() {
        //*Arrange
        EpisodeEntity episodeEntity = new EpisodeEntity();
        episodeEntity.setNumberEpisode(1);
        CharacterEntity entity = new CharacterEntity();
        entity.setEpisodes(Set.of(episodeEntity));
        //* Act & Assert
        assertThatThrownBy(() -> repository.save(entity)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenListCharacterWithNameRepeated_whenPersistAll_thenThrowException() {
    }

    @Test
    void givenNameOfCharacter_whenSearchInDbAndFoundOne_thenReturnCharacter() throws NameNotFoundException {
        //* Arrange
        CharacterEntity entity = new CharacterEntity();
        entity.setName("ivan");
        CharacterEntity persisted = repository.save(entity);
        //* Act
        CharacterEntity characterFound = repository.findByName(entity.getName());
        //* Assert
        assertThat(characterFound).as("Validate that found the same character persisted").withFailMessage("The information of entities are not the same").isEqualTo(persisted);
    }

    @Test
    void givenNameOfCharacter_whenSearchInDbAndNotFoundAny_thenThrowNameNotFoundException() {
        //* Act & Assert
        assertThatThrownBy(() -> repository.findByName("ivan")).isInstanceOf(NameNotFoundException.class);
    }

}
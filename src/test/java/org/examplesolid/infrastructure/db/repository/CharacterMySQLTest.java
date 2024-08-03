package org.examplesolid.infrastructure.db.repository;

import org.assertj.core.api.Assertions;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=create-drop"})
class CharacterMySQLTest {

    @SpyBean
    private CharacterMySQL repository;

    @Test
    void whenSearchACharacterByName_FindOneSuccessfully(){
        //*Arrange
        repository.save(new CharacterEntity(null,"name1",Collections.emptyList()));
        //*Act
        CharacterEntity entityFind = repository.findByName("name1");
        //*Assert
        Assertions.assertThat(entityFind.getUuid()).isNotNull();
        Assertions.assertThat(entityFind.getName()).isEqualTo("name1");
    }

    @Test
    void whenSearchACharacterByName_NotFindOne(){
        //*Act
        CharacterEntity entityFind = repository.findByName("name1");
        //*Assert
        Assertions.assertThat(entityFind).isNull();
    }

    @Test
    void whenSearchAllCharactersByName_FindAllSuccesfully(){
        //*Arrange
        List<CharacterEntity> toSave = List.of(new CharacterEntity(null, "name1", Collections.emptyList()), new CharacterEntity(null, "name2", Collections.emptyList()), new CharacterEntity(null, "name3", Collections.emptyList()));
        repository.saveAll(toSave);
        Pageable pageable = PageRequest.of(0,10);
        //*Act
        Page<CharacterEntity> found = repository.findAll(pageable);
        //*Assert
        Assertions.assertThat(found)
                .withFailMessage("La lista recuperada esta vaciá")
                .isInstanceOfAny(Page.class)
                .isNotEmpty();
    }

    @Test
    void whenSaveMultipleCharacters_IsCorrectPersist() {
        //*Arrange
        List<CharacterEntity> toSave = List.of(new CharacterEntity(null, "name1", Collections.emptyList()), new CharacterEntity(null, "name2", Collections.emptyList()), new CharacterEntity(null, "name3", Collections.emptyList()));
        //*Act
        List<CharacterEntity> saved = repository.saveAll(toSave);
        //*Assert
        Assertions.assertThat(saved).withFailMessage("El resultado después de persistir esta vació").isNotEmpty();
    }

    @Test
    void whenSaveACharacterWithOutName_ThrowException() {
        Assertions.assertThatThrownBy(() -> repository.save(new CharacterEntity())).isInstanceOf(Exception.class);
    }

    @Test
    void whenSaveCharacter_IsCorrectPersist() {
        //*Averrage
        CharacterEntity toSave = new CharacterEntity(null, "nombre", Collections.emptyList());
        //*Act
        CharacterEntity saved = repository.save(toSave);
        //*Assert
        Assertions.assertThat(saved.getUuid()).withFailMessage("El UUID es nulo o esta vació").isNotNull();
        Assertions.assertThat(saved.getName()).withFailMessage("No se guardo correctamente al personaje").isEqualTo(toSave.getName());
    }
}
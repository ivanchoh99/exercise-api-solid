package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

import static org.examplesolid.domain.util.Constants.SEPARATOR_CONCAT_FUNFACT;

@Entity
@Table(name = "character_tv")
@Getter
@Setter
@NoArgsConstructor
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "character", cascade = CascadeType.ALL)
    private Set<EpisodeEntity> episodes;
    private String funFacts;

    public void addFunFact(String funFact) {
        if (this.funFacts == null) {
            this.funFacts = funFact;
        } else {
            this.funFacts = funFacts.concat(SEPARATOR_CONCAT_FUNFACT).concat(funFact);
        }
    }
}

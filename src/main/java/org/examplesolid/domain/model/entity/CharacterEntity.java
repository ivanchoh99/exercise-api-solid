package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.examplesolid.domain.util.Constants.SEPARATOR_CONCAT_FUNFACT;

@Entity
@Table(name = "character_tv", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@NoArgsConstructor
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Setter
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Setter
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

    public CharacterEntity(UUID uuid, String name, String funFact) {
        this.uuid = uuid;
        this.name = name;
        this.addFunFact(funFact);
    }

    public CharacterEntity(String name, String funFacts, Set<Integer> episodes) {
        this.name = name;
        this.funFacts = funFacts;
        this.episodes = new HashSet<>();
        for (Integer episode : episodes) {
            EpisodeEntity episodeEntity = new EpisodeEntity(episode, this);
            this.episodes.add(episodeEntity);
        }
    }

    @Override
    public String toString() {
        return "CharacterEntity{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", episodes=" + episodes +
                ", funFacts='" + funFacts + '\'' +
                '}';
    }
}

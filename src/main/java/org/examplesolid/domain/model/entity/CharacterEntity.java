package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.examplesolid.application.util.Factutility;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private String funFacts = "";

    public void addFunFact(String funFact) {
        this.funFacts = Factutility.addFactToString(this.funFacts, funFact);
    }

    public CharacterEntity(UUID uuid, String name, String funFact) {
        this.uuid = uuid;
        this.name = name;
        this.addFunFact(funFact);
    }

    public CharacterEntity(String name, String funFacts, Set<Integer> episodes) {
        this.name = name;
        this.funFacts = funFacts;
        this.episodes = episodes.stream().map(episode -> new EpisodeEntity(episode, this)).collect(Collectors.toSet());
    }
}

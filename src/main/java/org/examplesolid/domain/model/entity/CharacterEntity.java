package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "character_tv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "CHARACTER_EPISODE",
            joinColumns = @JoinColumn(name = "CHARACTER_UUID", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "EPISODE_UUID", referencedColumnName = "uuid")
    )
    private List<EpisodeEntity> episodes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "CHARACTER_FUNFACTS",
            joinColumns = @JoinColumn(name = "CHARACTER_UUID", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "FUN_FACTS_UUID", referencedColumnName = "uuid")
    )
    private List<FunFacts> funFacts;

    public CharacterEntity(String name, List<String> episodes) {
        this.name = name;
        this.episodes = episodes.stream().map(EpisodeEntity::new).toList();
    }
}

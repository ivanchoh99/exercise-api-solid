package org.examplesolid.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "character_episodes")
@NoArgsConstructor
public class EpisodeEntity {
    @Id
    @Column(name = "number", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "character_uuid", nullable = false)
    private CharacterEntity character;
    @Column(name = "number_episode")
    private Integer numberEpisode;
}

package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "character_tv")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EpisodeEntity> episodes;
}

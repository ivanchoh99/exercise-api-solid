package org.examplesolid.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "character_tv", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NoArgsConstructor
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    private String status;
    private String specie;
    private String gender;
    private String origin;
    private String location;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "character")
    private Set<EpisodeEntity> episodes;
    private String facts;
}

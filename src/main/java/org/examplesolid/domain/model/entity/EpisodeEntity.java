package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name ="episode")
@NoArgsConstructor
public class EpisodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(unique = true, nullable = false)
    private int numero;
}

package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name ="episode")
public class EpisodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(unique = true)
    private String url;
}

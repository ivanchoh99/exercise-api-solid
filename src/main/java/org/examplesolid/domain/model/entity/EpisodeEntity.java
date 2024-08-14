package org.examplesolid.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "episodes")
@Getter
@NoArgsConstructor
public class EpisodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(unique = true, name = "number")
    private Integer number;

    public EpisodeEntity(String number){
        this.number = Integer.getInteger(number);
    }
}

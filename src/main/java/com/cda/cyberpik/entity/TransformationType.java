package com.cda.cyberpik.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transformation_type")
public class TransformationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transformation_type_id")
    private Long transformationTypeId;
    private String title;

    @OneToMany
    @JoinColumn(name = "transformation_id")
    private List<Transformation> transformations;
}

package com.cda.cyberpik.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

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

    @OneToMany(cascade=ALL, mappedBy="transformationType")
    private List<Transformation> transformations;
}

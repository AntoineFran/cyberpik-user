package com.cda.cyberpik.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transformation_id")
    private Long transformationID;
    private String title;

    @ManyToOne
    @JoinColumn(name="transformation_type_id", nullable=false)
    private TransformationType transformationType;
}

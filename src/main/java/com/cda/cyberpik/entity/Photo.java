package com.cda.cyberpik.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;
    private String title;

    @Column(name = "photo_url")
    private String photoUrl;

    @Lob
    @Column(name = "photo_bytes")
    private byte[] photoBytes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name="format_id", nullable=false)
    private Format format;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "photo_transformations",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "transformation_id"))
    List<Transformation> photoTransformations;
}

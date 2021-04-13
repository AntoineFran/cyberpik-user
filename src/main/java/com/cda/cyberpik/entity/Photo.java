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
    
    @Lob
    @Column(name = "image_bytes")
    private byte[] imageBytes;
    
    @Column(name = "is_profile_picture", nullable = false)
    private boolean isProfilePicture;

    @OneToOne
    private Location location;
    
    @ManyToOne(optional = false)
    private UserAccount userAccount;
    
    @ManyToOne(optional = false)
    private Format format;

    @OneToMany
    @JoinTable(
            name = "photos_transformations",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "transformation_id"))
    List<Transformation> photoTransformations;
}

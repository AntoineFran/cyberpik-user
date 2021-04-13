package com.cda.cyberpik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    @ManyToOne
    @Column(nullable = false)
    private UserAccount userAccount;
    
    @ManyToOne    
    @Column(nullable = false)
    private Format format;
    
}

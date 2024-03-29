package com.cda.cyberpik.entity;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long userAccountId;
    
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    
    @Column(nullable = false)
    private String password;
  
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "is_archived")
    private boolean isArchived = false;
    
    @Column(name = "is_admin")
    private boolean isAdmin = false;

    private String location;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
	private List<Photo> photos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="photo_profile_id")
    private Photo profilePhoto;
}

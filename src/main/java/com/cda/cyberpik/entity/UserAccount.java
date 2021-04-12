package com.cda.cyberpik.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long userAccountId;
    private String username;
    private String password;
  
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "is_archived")
    private boolean isArchived;
    
    @Column(name = "is_admin")
    private boolean isAdmin;
    
    @Column(name = "enable_newsletter")
    private boolean enableNewsletter;
    
    @OneToOne
    private City city;
    
    @OneToMany
	@JoinColumn(name = "photo_id")
	private List<Photo> photos;
}

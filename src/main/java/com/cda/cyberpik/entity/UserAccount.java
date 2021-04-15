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
    
    @Column(name = "enable_newsletter")
    private boolean enableNewsletter = false;
    
    @OneToOne
    @JoinColumn(name="city_id", nullable=false)
    private City city;
    
    @OneToMany(cascade=ALL, mappedBy="userAccount")
	private List<Photo> photos;
}

package com.cda.cyberpik.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    private Country country;
}

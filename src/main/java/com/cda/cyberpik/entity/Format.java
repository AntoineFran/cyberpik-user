package com.cda.cyberpik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "format_id")
    private Long formatId;
    
    @Column(nullable = false)
    private String name;
}

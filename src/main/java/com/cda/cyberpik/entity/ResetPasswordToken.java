package com.cda.cyberpik.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String token;

    private String email;

    @Column(name = "expire_date")
    private Date expiryDate = Date.from(LocalDateTime.now().plus(EXPIRATION, ChronoUnit.MINUTES)
            .atZone(ZoneId.systemDefault())
            .toInstant());
}
package com.smartgym.smartgym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "membership_plans")
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    // e.g. 30, 90, 365
    @Min(1)
    @Column(nullable = false)
    private int durationDays;

    @Min(0)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}

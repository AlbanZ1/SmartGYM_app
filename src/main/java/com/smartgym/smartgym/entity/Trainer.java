package com.smartgym.smartgym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trainers", uniqueConstraints = {
        @UniqueConstraint(name = "uk_trainer_email", columnNames = "email")
})
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    private String specialty;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;
}

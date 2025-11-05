package org.l0gik67.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.l0gik67.converter.BirthDateConverter;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    private String username;

    private PersonalInfo personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
}

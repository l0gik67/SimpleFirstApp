package org.l0gik67.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.l0gik67.converter.BirthDateConverter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PersonalInfo {
    private String firstname;
    private String lastname;
    @Convert(converter = BirthDateConverter.class)
    @Column(name = "birth_date")
    private Birthday birthday;
}

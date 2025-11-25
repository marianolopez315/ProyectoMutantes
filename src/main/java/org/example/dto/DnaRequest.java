package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validation.ValidDna;

import java.io.Serializable;

@Data
public class DnaRequest implements Serializable {
    @NotNull(message = "El array de ADN no puede ser nulo")
    @ValidDna
    private String []dna;
}

package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validation.ValidDna;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Data
public class DnaRequest implements Serializable {
    @NotNull(message = "El array de ADN no puede ser nulo")
    @ValidDna
    @Schema(
            description = "Secuencia de ADN representada como un array de Strings NxN",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]"
    )
    private String []dna;
}

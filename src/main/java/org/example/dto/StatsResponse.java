package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estadísticas de verificaciones de ADN")
public class StatsResponse {

    @Schema(description = "Cantidad de ADNs mutantes detectados")
    private long count_mutant_dna;
    @Schema(description = "Cantidad de ADNs humanos detectados")
    private long count_human_dna;
    @Schema(description = "Ratio de mutantes sobre humanos (mutantes / humanos)")
    private double ratio;
}
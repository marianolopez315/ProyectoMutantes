package org.example.controller;

import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantController {

    private final MutantService mutantService;

    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    // Endpoint Nivel 2
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.analyzeDna(dnaRequest.getDna());

        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Endpoint Nivel 3 (El que te faltaba)
    @GetMapping("/stats")
    public StatsResponse getStats() {
        return mutantService.getStats();
    }
}

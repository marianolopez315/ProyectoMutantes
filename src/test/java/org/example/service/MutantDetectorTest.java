package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontales y verticales")
    void testMutantHorizontalAndVertical() {
        String[] dna = {
                "AAAAAA", // Horizontal
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA", // Horizontal
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Debería ser mutante por secuencias horizontales");
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales")
    void testMutantDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA", // Horizontal
                "TCACTG"
        };
        // Nota: Este caso específico del ejemplo tiene una diagonal (A-A-A-A) y una horizontal (C-C-C-C)
        assertTrue(mutantDetector.isMutant(dna), "Debería ser mutante por cruce de secuencias");
    }

    @Test
    @DisplayName("NO debe detectar mutante si solo hay una secuencia")
    void testHumanWithOneSequence() {
        String[] dna = {
                "AAAAAA", // Solo una secuencia horizontal aquí
                "CAGTGC",
                "GTATGT",
                "AGAAGG",
                "CTCCTA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna), "Humano con solo una secuencia debería retornar false");
    }

    @Test
    @DisplayName("NO debe detectar mutante si no hay secuencias")
    void testHumanNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Validación: Debe retornar false con ADN nulo")
    void testNullDna() {
        assertFalse(mutantDetector.isMutant(null));
    }

    @Test
    @DisplayName("Validación: Debe retornar false con ADN vacío")
    void testEmptyDna() {
        assertFalse(mutantDetector.isMutant(new String[]{}));
    }

    @Test
    @DisplayName("Validación: Debe retornar false con caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAXGG", // X es inválido
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna), "Debería fallar por contener caracteres inválidos (X)");
    }

    @Test
    @DisplayName("Validación: Debe retornar false si la matriz no es NxN")
    void testNonNxNMatrix() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT" // Faltan filas para ser 6x6
        };
        assertFalse(mutantDetector.isMutant(dna), "Debería fallar si no es cuadrada");
    }
}

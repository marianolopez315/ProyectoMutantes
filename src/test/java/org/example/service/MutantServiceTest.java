package org.example.service;

import org.example.dto.StatsResponse;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Habilita Mockito
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector; //Simulamos el detector (no ejecuta código real)

    @Mock
    private DnaRecordRepository dnaRecordRepository; //Simulamos la BD (no conecta de verdad)

    @InjectMocks
    private MutantService mutantService; //Esta es la clase REAL que vamos a probar

    @Test
    @DisplayName("Si el ADN es nuevo y Mutante: debe detectar, guardar y devolver true")
    void testAnalyzeNewMutantDna() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        //ARRANGE (Preparar simulaciones)
        //Simulamos que NO existe en BD
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        //Simulamos que el detector dice "Es Mutante"
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        //ACT (Ejecutar)
        boolean result = mutantService.analyzeDna(dna);

        //ASSERT (Verificar)
        assertTrue(result);

        //Verificamos que se haya llamado a guardar en BD
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Si el ADN es nuevo y Humano: debe detectar, guardar y devolver false")
    void testAnalyzeNewHumanDna() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        //Simulamos que NO existe en BD
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        //Simulamos que el detector dice "Es Humano"
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Caché: Si el ADN ya existe, NO debe llamar al detector de nuevo")
    void testAnalyzeCachedDna() {
        String[] dna = {"AAAAAA", "CCCCCC", "TTTTTT", "GGGGGG", "AAAAAA", "CCCCCC"};

        // Simulamos que YA existe en BD como Mutante
        DnaRecord existingRecord = new DnaRecord("hash123", true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));


        boolean result = mutantService.analyzeDna(dna);


        assertTrue(result);

        //Verificamos que el detector NUNCA se ejecutó
        verify(mutantDetector, never()).isMutant(any());
        //Verificamos que NO se intentó guardar de nuevo
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    @DisplayName("Estadísticas: Debe calcular el ratio correctamente")
    void testGetStats() {
        //Simulamos que la BD devuelve 40 mutantes y 100 humanos
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse stats = mutantService.getStats();

        assertEquals(40, stats.getCount_mutant_dna());
        assertEquals(100, stats.getCount_human_dna());
        assertEquals(0.4, stats.getRatio()); // 40 / 100 = 0.4
    }

    @Test
    @DisplayName("Estadísticas: Debe manejar división por cero (0 humanos)")
    void testGetStatsDivisionByZero() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L); //0 humanos

        StatsResponse stats = mutantService.getStats();

        assertEquals(10, stats.getCount_mutant_dna());
        assertEquals(0, stats.getCount_human_dna());
        //Según nuestra lógica, si no hay humanos, el ratio es igual a los mutantes (o podrías definirlo como 0)
        assertEquals(10.0, stats.getRatio());
    }
}

package org.example.controller;

import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class) // Probamos SOLO el controlador
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula el navegador/Postman

    @MockBean
    private MutantService mutantService; // Simulamos el servicio (ya lo probamos antes)

    @Test
    @DisplayName("POST /mutant: Retorna 200 OK si es mutante")
    void testCheckMutantReturn200() throws Exception {
        // Simulamos que el servicio responde TRUE (Es Mutante)
        when(mutantService.analyzeDna(any())).thenReturn(true);

        String jsonRequest = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk()); // Esperamos HTTP 200
    }

    @Test
    @DisplayName("POST /mutant: Retorna 403 Forbidden si es humano")
    void testCheckMutantReturn403() throws Exception {
        // Simulamos que el servicio responde FALSE (Es Humano)
        when(mutantService.analyzeDna(any())).thenReturn(false);

        String jsonRequest = "{\"dna\":[\"GTGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCTTA\",\"TCACTG\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden()); // Esperamos HTTP 403
    }

    @Test
    @DisplayName("POST /mutant: Retorna 400 Bad Request si el ADN es inválido (vacío)")
    void testCheckMutantInvalidInput() throws Exception {
        // Enviamos un JSON con array vacío para activar la validación @NotEmpty del DTO
        String jsonRequest = "{\"dna\":[]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()); // Esperamos HTTP 400
    }

    @Test
    @DisplayName("GET /stats: Retorna estadísticas correctamente")
    void testGetStats() throws Exception {
        // Preparamos una respuesta simulada
        StatsResponse mockStats = new StatsResponse(40, 100, 0.4);
        when(mutantService.getStats()).thenReturn(mockStats);

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }
}

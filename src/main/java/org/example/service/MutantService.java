package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.example.dto.StatsResponse;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public MutantService(MutantDetector mutantDetector, DnaRecordRepository dnaRecordRepository) {
        this.mutantDetector = mutantDetector;
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public boolean analyzeDna(String[] dna) {
        // 1. Calcular el Hash del ADN
        String dnaHash = calculateHash(dna);

        // 2. Verificar si ya existe en la base de datos (Caché)
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        if (existingRecord.isPresent()) {
            // Si ya existe, devolvemos el resultado guardado sin re-analizar
            return existingRecord.get().isMutant();
        }

        // 3. Si no existe, ejecutamos el análisis
        boolean isMutant = mutantDetector.isMutant(dna);

        // 4. Guardamos el resultado para el futuro
        DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
        dnaRecordRepository.save(newRecord);

        return isMutant;
    }

    // Método auxiliar para generar un SHA-256 del array
    private String calculateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Concatenamos todo el array en un solo string para hashearlo
            StringBuilder sb = new StringBuilder();
            for (String s : dna) {
                sb.append(s);
            }
            byte[] encodedhash = digest.digest(sb.toString().getBytes());

            // Convertimos bytes a Hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular hash SHA-256", e);
        }
    }
    public StatsResponse getStats() {
        // 1. Consultamos a la BD usando los métodos del Repositorio
        long countMutant = dnaRecordRepository.countByIsMutant(true);
        long countHuman = dnaRecordRepository.countByIsMutant(false);

        double ratio = 0.0;

        // 2. Calculamos el ratio (evitando dividir por cero)
        if (countHuman > 0) {
            ratio = (double) countMutant / countHuman;
        } else if (countMutant > 0) {
            // Si hay mutantes pero 0 humanos, el ratio es infinito o igual a la cantidad
            ratio = (double) countMutant;
        }

        // 3. Retornamos el objeto DTO con los datos
        return new StatsResponse(countMutant, countHuman, ratio);
    }
}


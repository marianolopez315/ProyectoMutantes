package org.example.repository;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    // Buscar por hash (para saber si ya lo analizamos)
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    // Contar para las estadísticas (cuántos mutantes/humanos hay)
    long countByIsMutant(boolean isMutant);
}

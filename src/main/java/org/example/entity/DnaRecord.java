package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "dna_records", indexes = {
        @Index(name = "idx_dna_hash", columnList = "dna_hash")//El indice se crea para que la busqueda por hash sea rápida
})
@Data
@NoArgsConstructor
//Esta es la tabla donde vamos a guardar los datos
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dna_hash", unique = true, nullable = false)
    private String dnaHash;

    @Column(name = "esMutante", nullable = false)
    private boolean isMutant;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public DnaRecord(String dnaHash, boolean isMutant) {
        this.dnaHash = dnaHash;
        this.isMutant = isMutant;
        this.createdAt = LocalDateTime.now();
    }

}

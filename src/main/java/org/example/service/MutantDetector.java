package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    private static final int LONGITUD_SECUENCIA = 4;

    public boolean isMutant (String[] dna){
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;
        char[][] matriz = new char[n][n];

        for (int i = 0; i < n; i++) {
            String fila = dna[i];

            //Asegurar que la matriz sea cuadrada
            if (fila == null || fila.length() != n) {
                return false; //puedo largar una excepcion aca
            }

            //Convertimos la fila a char[] para meterla en la matriz
            matriz[i] = fila.toCharArray();

            for (char c : matriz[i]) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false;
                }
            }
        }

        return verificarSecuencias(matriz, n);
    }


    private boolean verificarSecuencias(char[][] matriz, int n) {
        int contadorSecuencias = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                //HORIZONTAL
                if (j <= n - LONGITUD_SECUENCIA) {
                    if (checkHorizontal(matriz, i, j)) {
                        contadorSecuencias++;
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }

                //VERTICAL
                if (i <= n - LONGITUD_SECUENCIA) {
                    if (checkVertical(matriz, i, j)) {
                        contadorSecuencias++;
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }

                //DIAGONAL PRINCIPAL
                if (i <= n - LONGITUD_SECUENCIA && j <= n - LONGITUD_SECUENCIA) {
                    if (checkDiagonal(matriz, i, j)) {
                        contadorSecuencias++;
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }

                //DIAGONAL INVERSA
                if (i >= LONGITUD_SECUENCIA - 1 && j <= n - LONGITUD_SECUENCIA) {
                    if (checkDiagonalInversa(matriz, i, j)) {
                        contadorSecuencias++;
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(char[][] matriz, int i, int j) {
        char base = matriz[i][j];
        return base == matriz[i][j+1] &&
                base == matriz[i][j+2] &&
                base == matriz[i][j+3];
    }
    private boolean checkVertical(char[][] matriz, int i, int j) {
        char base = matriz[i][j];
        return base == matriz[i+1][j] &&
                base == matriz[i+2][j] &&
                base == matriz[i+3][j];
    }
    private boolean checkDiagonal(char[][] matriz, int i, int j) {
        char base = matriz[i][j];
        return base == matriz[i+1][j+1] &&
                base == matriz[i+2][j+2] &&
                base == matriz[i+3][j+3];
    }
    private boolean checkDiagonalInversa(char[][] matriz, int i, int j) {
        char base = matriz[i][j];
        return base == matriz[i-1][j+1] &&
                base == matriz[i-2][j+2] &&
                base == matriz[i-3][j+3];
    }
}

package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ValidDnaValidator implements ConstraintValidator<ValidDna, String[]> {

    private static final Set<Character> VALID_CHARACTERS = Set.of('A', 'T', 'C', 'G');

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        //Validar nulo o vacío
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;

        for (String row : dna) {
            if (row == null || row.length() != n) {
                return false;
            }

            //Validar caracteres (A, T, C, G)
            for (char c : row.toCharArray()) {
                //Convertimos a mayúscula
                if (!VALID_CHARACTERS.contains(Character.toUpperCase(c))) {
                    return false;
                }
            }
        }

        return true; //Si pasa todo, es válido
    }
}
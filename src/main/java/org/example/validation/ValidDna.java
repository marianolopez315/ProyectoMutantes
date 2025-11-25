package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidDnaValidator.class) //Aca conectamos con la lógica
@Target({ElementType.FIELD}) //Solo se puede usar en atributos (fields)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDna {

    String message() default "La secuencia de ADN es inválida. Solo se aceptan los caracteres: A, T, C, G";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
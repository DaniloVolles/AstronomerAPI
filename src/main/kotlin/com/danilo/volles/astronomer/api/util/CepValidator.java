package com.danilo.volles.astronomer.api.util;

public final class CepValidator {

    public static boolean isCodeBrazilian(final String cep) {
        return cep != null && cep.matches("^\\d{5}-?\\d{3}$");
    }
}

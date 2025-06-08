package com.danilo.volles.astronomer.api.util;

import com.danilo.volles.astronomer.api.exception.InvalidCepCodeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CepValidator {

    public static void verifyCepCode(String cep) {
        if (!isCodeBrazilian(cep)) {
            log.error("Brazilian CEP code expected");
            throw new InvalidCepCodeException();
        }
    }

    private static boolean isCodeBrazilian(final String cep) {
        return cep != null && cep.matches("^\\d{5}-?\\d{3}$");
    }
}

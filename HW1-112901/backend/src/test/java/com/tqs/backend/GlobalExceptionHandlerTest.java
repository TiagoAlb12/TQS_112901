package com.tqs.backend;

import com.tqs.backend.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleIllegalState() {
        var res = handler.handleIllegalState(new IllegalStateException("Estado inválido"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testHandleDuplicateReservation() {
        var res = handler.handleDuplicateReservation(new DataIntegrityViolationException("duplicado"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testHandleIllegalArgument() {
        var res = handler.handleIllegalArgument(new IllegalArgumentException("tipo errado"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testHandleResponseStatusException() {
        var res = handler.handleResponseStatusException(new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testHandleMissingParams() {
        var ex = new MissingServletRequestParameterException("restaurantId", "Long");
        var res = handler.handleMissingParams(ex);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(res.getBody()).contains("Falta o parâmetro obrigatório");
    }

    @Test
    void testHandleGenericException() {
        var res = handler.handleGenericException(new RuntimeException("erro genérico"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(res.getBody()).contains("Erro interno no servidor");
    }
}

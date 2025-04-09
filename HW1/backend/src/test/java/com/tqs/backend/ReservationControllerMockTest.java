package com.tqs.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs.backend.controller.ReservationController;
import com.tqs.backend.enums.MealType;
import com.tqs.backend.model.ReservationRequestDTO;
import com.tqs.backend.model.ReservationResponseDTO;
import com.tqs.backend.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 OK ao criar reserva com sucesso")
    void testCreateReservation_Success() throws Exception {
        ReservationResponseDTO response = new ReservationResponseDTO("token123", "Cantina", LocalDate.now(), false, "ALMOCO");
        Mockito.when(reservationService.createReservation(any())).thenReturn(response);

        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now());
        request.setType(MealType.ALMOCO.name());

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.type").value("ALMOCO"));
    }

    @Test
    @DisplayName("Deve retornar 404 NOT FOUND ao criar reserva com restaurante inválido")
    void testCreateReservation_IllegalArgument() throws Exception {
        Mockito.when(reservationService.createReservation(any())).thenThrow(new IllegalArgumentException("restaurante não encontrado"));

        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(99L);
        request.setDate(LocalDate.now());
        request.setType(MealType.JANTAR.name());

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 BAD REQUEST ao criar reserva inválida (ex: duplicada)")
    void testCreateReservation_IllegalState() throws Exception {
        Mockito.when(reservationService.createReservation(any())).thenThrow(new IllegalStateException("Reserva já existe"));

        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now());
        request.setType(MealType.ALMOCO.name());

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro: Reserva já existe"));
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao obter reserva com token válido")
    void testGetReservation_Success() throws Exception {
        ReservationResponseDTO response = new ReservationResponseDTO("token123", "Cantina", LocalDate.now(), false, "JANTAR");
        Mockito.when(reservationService.getReservationByToken("token123")).thenReturn(response);

        mockMvc.perform(get("/api/reservations/token123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.type").value("JANTAR"));
    }

    @Test
    @DisplayName("Deve retornar 404 NOT FOUND ao obter reserva com token inválido")
    void testGetReservation_InvalidToken() throws Exception {
        Mockito.when(reservationService.getReservationByToken("invalid")).thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/api/reservations/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao cancelar reserva com sucesso")
    void testCancelReservation_Success() throws Exception {
        mockMvc.perform(delete("/api/reservations/token123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reserva cancelada com sucesso."));
    }

    @Test
    @DisplayName("Deve retornar 400 BAD REQUEST ao cancelar reserva com estado inválido")
    void testCancelReservation_IllegalState() throws Exception {
        Mockito.doThrow(new IllegalStateException("Já cancelada")).when(reservationService).cancelReservation("token123");

        mockMvc.perform(delete("/api/reservations/token123"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro: Já cancelada"));
    }

    @Test
    @DisplayName("Deve retornar 404 NOT FOUND ao cancelar reserva com token inválido")
    void testCancelReservation_IllegalArgument() throws Exception {
        Mockito.doThrow(new IllegalArgumentException()).when(reservationService).cancelReservation("token123");

        mockMvc.perform(delete("/api/reservations/token123"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao fazer check-in com sucesso")
    void testCheckInReservation_Success() throws Exception {
        mockMvc.perform(post("/api/reservations/checkin/token123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Check-in realizado com sucesso."));
    }

    @Test
    @DisplayName("Deve retornar 404 NOT FOUND ao fazer check-in com token inválido")
    void testCheckInReservation_IllegalArgument() throws Exception {
        Mockito.doThrow(new IllegalArgumentException()).when(reservationService).checkInReservation("fake");

        mockMvc.perform(post("/api/reservations/checkin/fake"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 BAD REQUEST ao fazer check-in de reserva inválida")
    void testCheckInReservation_IllegalState() throws Exception {
        Mockito.doThrow(new IllegalStateException("Já usada")).when(reservationService).checkInReservation("token123");

        mockMvc.perform(post("/api/reservations/checkin/token123"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro: Já usada"));
    }

    @Test
    @DisplayName("Deve lançar exceção genérica ao forçar erro")
    void testForceGenericError() throws Exception {
        mockMvc.perform(get("/api/reservations/force-error"))
                .andExpect(status().isInternalServerError());
    }
}

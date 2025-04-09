package com.tqs.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs.backend.enums.MealType;
import com.tqs.backend.model.ReservationRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReservationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Teste para a criação válida de uma reserva
    @Test
    void testCreateReservation_Success() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now().plusDays(1));
        request.setType(MealType.ALMOCO.name());

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.restaurantName").exists())
                .andExpect(jsonPath("$.type").value("ALMOCO"));
    }

    // Teste de erro ao reservar uma refeição num restaurante inexistente
    @Test
    void testCreateReservation_InvalidRestaurant() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(9999L); // Restaurante inválido
        request.setDate(LocalDate.now().plusDays(1));
        request.setType(MealType.ALMOCO.name());

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    // Teste de erro ao efetuar check-in com um token inválido
    @Test
    void testCheckIn_InvalidToken() throws Exception {
        mockMvc.perform(post("/api/reservations/checkin/fake-token"))
                .andExpect(status().isNotFound());
    }

    // Teste para procura de reserva com um token inválido
    @Test
    void testGetReservation_InvalidToken() throws Exception {
        mockMvc.perform(get("/api/reservations/invalid-token"))
                .andExpect(status().isNotFound());
    }

    // Teste para o cancelamento correto de uma reserva
    @Test
    void testCancelReservation_Success() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now().plusDays(2));
        request.setType(MealType.JANTAR.name());

        String response = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        mockMvc.perform(delete("/api/reservations/" + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("cancelada com sucesso")));
    }

    // Teste para um check-in correto de uma reserva
    @Test
    void testCheckInReservation_Success() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now().plusDays(3));
        request.setType(MealType.ALMOCO.name());

        String response = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        mockMvc.perform(post("/api/reservations/checkin/" + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Check-in realizado com sucesso")));
    }

    // Teste de reserva duplicada
    @Test
    void testCreateReservation_DuplicateMealTypeSameDay() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now().plusDays(4));
        request.setType(MealType.JANTAR.name());

        // Primeira reserva (válida)
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Segunda reserva para o mesmo dia, restaurante e tipo
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void testCreateReservation_InvalidMealType() throws Exception {
        String payload = """
        {
            "restaurantId": 1,
            "date": "%s",
            "type": "BRUNCH"
        }
        """.formatted(LocalDate.now().plusDays(6));

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }


    // Teste para obter uma reserva com um token válido
    @Test
    void testGetReservation_ValidToken() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now().plusDays(5));
        request.setType(MealType.ALMOCO.name());

        String response = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        mockMvc.perform(get("/api/reservations/" + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.type").value("ALMOCO"));
    }


    @Test
    void testGetAllRestaurants() throws Exception {
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetMealsForRestaurant() throws Exception {
        mockMvc.perform(get("/api/meals?restaurantId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCancelReservation_InvalidToken() throws Exception {
        mockMvc.perform(delete("/api/reservations/invalid-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testHandleDataIntegrityViolationException() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now().plusDays(10));
        request.setType("ALMOCO");

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Já existe uma reserva ativa")));
    }

    @Test
    void testHandleGenericException() throws Exception {
        mockMvc.perform(get("/api/force-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Erro interno no servidor")));
    }
}

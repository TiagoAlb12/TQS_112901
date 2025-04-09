package com.tqs.backend;

import com.tqs.backend.entity.Reservation;
import com.tqs.backend.entity.Restaurant;
import com.tqs.backend.enums.MealType;
import com.tqs.backend.model.ReservationRequestDTO;
import com.tqs.backend.model.ReservationResponseDTO;
import com.tqs.backend.repository.ReservationRepository;
import com.tqs.backend.repository.RestaurantRepository;
import com.tqs.backend.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Restaurant mockRestaurant;

    @BeforeEach
    void setUp() {
        mockRestaurant = new Restaurant();
        mockRestaurant.setId(1L);
        mockRestaurant.setName("Cantina Santiago");
    }

    // Teste para verificar se a reserva foi criada com sucesso
    @Test
    void testCreateReservation_Success() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now());
        request.setType("ALMOCO");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(mockRestaurant));

        ReservationResponseDTO response = reservationService.createReservation(request);

        assertNotNull(response.getToken());
        assertEquals("Cantina Santiago", response.getRestaurantName());
        assertEquals("ALMOCO", response.getType());
        assertFalse(response.isCheckedIn());

        verify(restaurantRepository).findById(1L);
        verify(reservationRepository).save(any(Reservation.class));
    }

    // Teste para verificar se a reserva foi criada num restaurante inexistente
    @Test
    void testCreateReservation_RestaurantNotFound() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(99L);
        request.setDate(LocalDate.now());
        request.setType("JANTAR");

        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reservationService.createReservation(request)
        );

        verify(restaurantRepository).findById(99L);
        verify(reservationRepository, never()).save(any());
    }

    // Teste para verificar se a reserva foi criada num restaurante que já tem uma reserva ativa
    @Test
    void testCreateReservation_DuplicateMealTypeSameDay() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now());
        request.setType("ALMOCO");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(mockRestaurant));

        Reservation existingReservation = new Reservation();
        existingReservation.setRestaurant(mockRestaurant);
        existingReservation.setDate(request.getDate());
        existingReservation.setType(MealType.ALMOCO);
        existingReservation.setCancelled(false);

        when(reservationRepository.findByRestaurantIdAndDateAndTypeAndCancelledFalse(
                1L, request.getDate(), MealType.ALMOCO
        )).thenReturn(Optional.of(existingReservation));

        assertThrows(ResponseStatusException.class, () -> {
            reservationService.createReservation(request);
        });

        verify(restaurantRepository).findById(1L);
        verify(reservationRepository).findByRestaurantIdAndDateAndTypeAndCancelledFalse(
                1L, request.getDate(), MealType.ALMOCO);
        verify(reservationRepository, never()).save(any());
    }

    // Teste para pesquisar uma reserva a partir de um token válido
    @Test
    void testGetReservationByToken_Success() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setRestaurant(mockRestaurant);
        reservation.setDate(LocalDate.now());
        reservation.setCheckedIn(false);
        reservation.setType(MealType.ALMOCO);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        ReservationResponseDTO response = reservationService.getReservationByToken("abc123");

        assertEquals("abc123", response.getToken());
        assertEquals("ALMOCO", response.getType());

        verify(reservationRepository).findByToken("abc123");
    }

    // Teste para verificar o erro ao tentar pesquisar uma reserva com um token inválido
    @Test
    void testGetReservationByToken_NotFound() {
        when(reservationRepository.findByToken("not-found")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reservationService.getReservationByToken("not-found")
        );
    }

    // Teste para verificar o erro ao tentar cancelar uma reserva já usada
    @Test
    void testCancelReservation_TokenNotFound() {
        when(reservationRepository.findByToken("inexistente")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reservationService.cancelReservation("inexistente")
        );
    }

    // Teste para verificar se a reserva foi encontrada com sucesso
    @Test
    void testCancelReservation_Success() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCheckedIn(false);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation("abc123");

        assertTrue(reservation.isCancelled());
        verify(reservationRepository).findByToken("abc123");
        verify(reservationRepository).save(reservation);
    }

    // Teste para verificar se o check-in foi bem sucedido
    @Test
    void testCheckInReservation_Success() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCheckedIn(false);
        reservation.setCancelled(false);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        reservationService.checkInReservation("abc123");

        assertTrue(reservation.isCheckedIn());
        verify(reservationRepository).findByToken("abc123");
        verify(reservationRepository).save(reservation);
    }

    // Teste para verificar o erro ao tentar dar check-in numa reserva cancelada
    @Test
    void testCheckInReservation_Cancelled() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCheckedIn(false);
        reservation.setCancelled(true);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        assertThrows(IllegalStateException.class, () ->
                reservationService.checkInReservation("abc123")
        );

        verify(reservationRepository).findByToken("abc123");
        verify(reservationRepository, never()).save(any());
    }

    // Teste para verificar o erro ao tentar dar check-in numa reserva já usada
    @Test
    void testCheckInReservation_AlreadyUsed() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCheckedIn(true);
        reservation.setCancelled(false);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        assertThrows(IllegalStateException.class, () ->
                reservationService.checkInReservation("abc123")
        );

        verify(reservationRepository).findByToken("abc123");
        verify(reservationRepository, never()).save(any());
    }

    // Teste para verificar o erro ao tentar dar check-in numa reserva inexistente
    @Test
    void testCheckInReservation_TokenNotFound() {
        when(reservationRepository.findByToken("invalid")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reservationService.checkInReservation("invalid")
        );

        verify(reservationRepository).findByToken("invalid");
        verify(reservationRepository, never()).save(any());
    }

    // Teste para verificar o erro ao tentar criar uma reserva com um tipo de refeição inválido
    @Test
    void testCreateReservation_InvalidMealType() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setRestaurantId(1L);
        request.setDate(LocalDate.now());
        request.setType("INVALIDO");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(mockRestaurant));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            reservationService.createReservation(request);
        });

        assertEquals("Tipo de refeição inválido", ex.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }
}

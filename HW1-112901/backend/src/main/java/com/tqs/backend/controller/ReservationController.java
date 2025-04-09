package com.tqs.backend.controller;

import com.tqs.backend.model.ReservationRequestDTO;
import com.tqs.backend.model.ReservationResponseDTO;
import com.tqs.backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDTO request) {
        try {
            ReservationResponseDTO dto = reservationService.createReservation(request);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/{token}")
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable String token) {
        try {
            ReservationResponseDTO dto = reservationService.getReservationByToken(token);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<String> cancelReservation(@PathVariable String token) {
        try {
            reservationService.cancelReservation(token);
            return ResponseEntity.ok("Reserva cancelada com sucesso.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Endpoints para o staff do restaurante
    @PostMapping("/checkin/{token}")
    public ResponseEntity<String> checkInReservation(@PathVariable String token) {
        try {
            reservationService.checkInReservation(token);
            return ResponseEntity.ok("Check-in realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    // Apenas para fins de teste
    @GetMapping("/force-error")
    public void forceGenericError() {
        throw new RuntimeException("Erro genérico de teste");
    }
}

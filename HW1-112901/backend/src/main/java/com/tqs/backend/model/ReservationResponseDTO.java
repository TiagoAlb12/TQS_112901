package com.tqs.backend.model;

import java.time.LocalDate;

public class ReservationResponseDTO {
    private String token;
    private String restaurantName;
    private LocalDate date;
    private boolean checkedIn;
    private String type;

    public ReservationResponseDTO(String token, String restaurantName, LocalDate date, boolean checkedIn, String type) {
        this.token = token;
        this.restaurantName = restaurantName;
        this.date = date;
        this.checkedIn = checkedIn;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

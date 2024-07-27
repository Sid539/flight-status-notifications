package com.example.flightstatus.controller;

import com.example.flightstatus.model.Flight;
import com.example.flightstatus.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping("/api/flights")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @PostMapping("/api/flights")
    public Flight saveFlight(@RequestBody Flight flight) {
        return flightService.saveFlight(flight);
    }
}

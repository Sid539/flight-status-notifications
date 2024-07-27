package com.example.flightstatus.service;

import com.example.flightstatus.model.Flight;
import com.example.flightstatus.repository.FlightRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight saveFlight(Flight flight) {
        Flight existingFlight = flightRepository.findById(flight.getId()).orElse(null);
        if (existingFlight != null && existingFlight.getStatus() != flight.getStatus()) {
            sendNotification(flight);
        }
        return flightRepository.save(flight);
    }

    private void sendNotification(Flight flight) {
        String message = "Flight " + flight.getNumber() + " status changed to " + flight.getStatus();
        rabbitTemplate.convertAndSend("flightStatusQueue", message);
    }
}

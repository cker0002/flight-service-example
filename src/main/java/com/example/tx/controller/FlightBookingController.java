package com.example.tx.controller;

import com.example.tx.dto.FlightBookingRequest;
import com.example.tx.dto.FlightBookingResponse;
import com.example.tx.entity.PassengerInfo;
import com.example.tx.exception.ErrorResponse;
import com.example.tx.exception.NoPassengerExistsException;
import com.example.tx.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlightBookingController {

    @Autowired
    private FlightBookingService service;
    @PostMapping("/bookFlightTicket")
    public FlightBookingResponse bookFlight(@RequestBody FlightBookingRequest request){
        FlightBookingResponse response = service.bookFlight(request);
        return response;
    }

    @GetMapping("/getFlightDetailsBySourceDestination/{source}/{destination}")
    public List<PassengerInfo> getPassengerDetailsBySourceDestination(@PathVariable  String source,@PathVariable String destination){
        List<PassengerInfo> passengerInfoList = service.findPassengerDetails(source,destination);
        return passengerInfoList;
    }

    @GetMapping("/getFlightDetailsByAirLine/{airline}")
    public List<PassengerInfo> getPassengerDetailsByAirline(@PathVariable  String airline){
        List<PassengerInfo> passengerInfoList = service.findPassengerDetailsByAirline(airline);
        return passengerInfoList;
    }

    @PutMapping("/updateEmail/{name}/{email}")
    public void updatePassengerName(@PathVariable String name,@PathVariable String email){
        service.updatePassengerName(name,email);
    }

    @DeleteMapping("/delete/{email}")
    public void deletePassengerDetails(@PathVariable String email){
        service.deletePassengerDetails(email);

    }

    @ExceptionHandler(value
            = NoPassengerExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse
    handleNoPassengerExistsException(
            NoPassengerExistsException ex)
    {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
    }
}

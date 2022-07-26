package com.example.tx.service;

import com.example.tx.dto.FlightBookingRequest;
import com.example.tx.dto.FlightBookingResponse;
import com.example.tx.entity.PassengerInfo;
import com.example.tx.entity.PaymentInfo;
import com.example.tx.exception.NoPassengerExistsException;
import com.example.tx.repository.PassengerInfoRepository;
import com.example.tx.repository.PaymentInfoRepository;
import com.example.tx.util.PaymentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Transactional
@Service
public class FlightBookingService {

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    public FlightBookingResponse bookFlight(FlightBookingRequest request){
        PassengerInfo passengerInfo = request.getPassengerInfo();
        passengerInfo = passengerInfoRepository.save(passengerInfo);
        PaymentInfo paymentInfo = request.getPaymentInfo();
        PaymentUtil.validateCreditLimit(paymentInfo.getAccountNo(), passengerInfo.getFare());
        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfo.setAmount(passengerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);
        return new FlightBookingResponse("SUCCESS", passengerInfo.getFare(),UUID.randomUUID().toString().split("-")[0],passengerInfo);
    }

    public List<PassengerInfo> findPassengerDetails(String source, String destination) {
        List<PassengerInfo> passengerInfoList = passengerInfoRepository.findAllBySourceAndDestination(source,destination);
        if(passengerInfoList.isEmpty()){
            throw new NoPassengerExistsException("No passenger exists from "+source+" To "+destination);
        }
        return passengerInfoList;
    }

    public List<PassengerInfo> findPassengerDetailsByAirline(String airline) {
        List<PassengerInfo> passengerInfoList = passengerInfoRepository.findAllByAirline(airline);
        if(passengerInfoList.isEmpty()){
            throw new NoPassengerExistsException("No Passengers exists for "+airline);
        }
        return passengerInfoList;
    }


    public void updatePassengerName(String name, String email) {
        passengerInfoRepository.setPassengerNameByEmail(name,email);
    }

    public void deletePassengerDetails(String email) {
        passengerInfoRepository.deleteAllByEmail(email);
    }
}

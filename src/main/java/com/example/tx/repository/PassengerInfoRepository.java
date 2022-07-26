package com.example.tx.repository;

import com.example.tx.entity.PassengerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PassengerInfoRepository extends JpaRepository<PassengerInfo, Long> {
    @Modifying
    @Query(value = "update PASSENGER_INFO p set p.name=?1 where p.email = ?2", nativeQuery = true)
    void setPassengerNameByEmail(String name, String email);

    List<PassengerInfo> findAllByAirline(String airline);

    void deleteAllByEmail(String email);

    List<PassengerInfo> findAllBySourceAndDestination(String source, String destination);
}

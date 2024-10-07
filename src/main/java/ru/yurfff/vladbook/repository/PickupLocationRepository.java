package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yurfff.vladbook.model.PickupLocation;

import java.util.List;

public interface PickupLocationRepository extends JpaRepository<PickupLocation, Long> {
    List<PickupLocation> findByCity(String city);

    List<PickupLocation> findByPostalCode(String postalCode);

    List<PickupLocation> findByAddress(String address);

    List<PickupLocation> findByCityAndPostalCode(String city, String postalCode);
}

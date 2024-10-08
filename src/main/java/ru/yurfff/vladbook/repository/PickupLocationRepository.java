package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yurfff.vladbook.model.PickupLocation;

import java.util.List;

public interface PickupLocationRepository extends JpaRepository<PickupLocation, Long> {
    // Метод для поиска мест получения по городу
    List<PickupLocation> findByCity(String city);

    // Метод для поиска мест получения по почтовому индексу
    List<PickupLocation> findByPostalCode(String postalCode);

    // Метод для поиска мест получения по адресу
    List<PickupLocation> findByAddress(String address);

    // Метод для поиска мест получения по городу и почтовому индексу
    List<PickupLocation> findByCityAndPostalCode(String city, String postalCode);
}

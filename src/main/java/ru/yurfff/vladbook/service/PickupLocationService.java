package ru.yurfff.vladbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yurfff.vladbook.model.PickupLocation;
import ru.yurfff.vladbook.repository.PickupLocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PickupLocationService {

    private final PickupLocationRepository pickupLocationRepository;

    @Autowired
    public PickupLocationService(PickupLocationRepository pickupLocationRepository) {
        this.pickupLocationRepository = pickupLocationRepository;
    }

    // Получение всех пунктов выдачи
    public List<PickupLocation> findAll() {
        return pickupLocationRepository.findAll();
    }

    // Получение пункта выдачи по ID
    public Optional<PickupLocation> findById(Long id) {
        return pickupLocationRepository.findById(id);
    }

    // Поиск по городу
    public List<PickupLocation> findByCity(String city) {
        return pickupLocationRepository.findByCity(city);
    }

    // Поиск по почтовому коду
    public List<PickupLocation> findByPostalCode(String postalCode) {
        return pickupLocationRepository.findByPostalCode(postalCode);
    }

    // Поиск по адресу
    public List<PickupLocation> findByAddress(String address) {
        return pickupLocationRepository.findByAddress(address);
    }

    // Поиск по городу и почтовому коду
    public List<PickupLocation> findByCityAndPostalCode(String city, String postalCode) {
        return pickupLocationRepository.findByCityAndPostalCode(city, postalCode);
    }

    // Сохранение нового пункта выдачи
    public PickupLocation save(PickupLocation location) {
        return pickupLocationRepository.save(location);
    }

    // Обновление существующего пункта выдачи
    public PickupLocation update(Long id, PickupLocation location) {
        location.setId(id);
        return pickupLocationRepository.save(location);
    }

    // Удаление пункта выдачи по ID
    public void deleteById(Long id) {
        if (!pickupLocationRepository.existsById(id)) {
            throw new IllegalArgumentException("Pickup location not found with id " + id);
        }
        pickupLocationRepository.deleteById(id);
    }

    // Проверка существования пункта выдачи по ID
    public boolean existsById(Long id) {
        return pickupLocationRepository.existsById(id);
    }
}

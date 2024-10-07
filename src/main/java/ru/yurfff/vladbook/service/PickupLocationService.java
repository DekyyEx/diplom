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

    public List<PickupLocation> findAll() {
        return pickupLocationRepository.findAll();
    }

    public Optional<PickupLocation> findById(Long id) {
        return pickupLocationRepository.findById(id);
    }

    public List<PickupLocation> findByCity(String city) {
        return pickupLocationRepository.findByCity(city);
    }

    public PickupLocation save(PickupLocation location) {
        return pickupLocationRepository.save(location);
    }

    public PickupLocation update(Long id, PickupLocation location) {
        location.setId(id);
        return pickupLocationRepository.save(location);
    }

    public void deleteById(Long id) {
        pickupLocationRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return pickupLocationRepository.existsById(id);
    }

    public List<PickupLocation> findByCityAndPostalCode(String city, String postalCode) {
        return pickupLocationRepository.findByCityAndPostalCode(city, postalCode);
    }

    public List<PickupLocation> findByPostalCode(String postalCode) {
        return pickupLocationRepository.findByPostalCode(postalCode);
    }

    public List<PickupLocation> findByAddress(String address) {
        return pickupLocationRepository.findByAddress(address);
    }
}

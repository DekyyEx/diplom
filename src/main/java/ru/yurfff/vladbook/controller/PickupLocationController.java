package ru.yurfff.vladbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yurfff.vladbook.model.PickupLocation;
import ru.yurfff.vladbook.service.PickupLocationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pickup-locations")
public class PickupLocationController {

    private final PickupLocationService pickupLocationService;

    @Autowired
    public PickupLocationController(PickupLocationService pickupLocationService) {
        this.pickupLocationService = pickupLocationService;
    }

    @GetMapping
    public List<PickupLocation> getAllPickupLocations() {
        return pickupLocationService.findAll();
    }

    @GetMapping("/{id}")
    public PickupLocation getPickupLocationById(@PathVariable Long id) {
        return pickupLocationService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found"));
    }

    @GetMapping("/by-city/{city}")
    public List<PickupLocation> getPickupLocationsByCity(@PathVariable String city) {
        return pickupLocationService.findByCity(city);
    }

    @GetMapping("/by-city-postal/{city}/{postalCode}")
    public List<PickupLocation> getPickupLocationsByCityAndPostalCode(@PathVariable String city, @PathVariable String postalCode) {
        return pickupLocationService.findByCityAndPostalCode(city, postalCode);
    }

    @GetMapping("/by-postal/{postalCode}")
    public List<PickupLocation> getPickupLocationsByPostalCode(@PathVariable String postalCode) {
        return pickupLocationService.findByPostalCode(postalCode);
    }

    @GetMapping("/by-address/{address}")
    public List<PickupLocation> getPickupLocationsByAddress(@PathVariable String address) {
        return pickupLocationService.findByAddress(address);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // Устанавливаем код ответа 201 (Created)
    public PickupLocation createPickupLocation(@RequestBody PickupLocation location) {
        if (location == null || location.getAddress() == null || location.getCity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pickup location data is incomplete");
        }
        return pickupLocationService.save(location);
    }

    @PutMapping("/{id}")
    public PickupLocation updatePickupLocation(@PathVariable Long id, @RequestBody PickupLocation location) {
        // Проверка существования записи
        if (!pickupLocationService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found for update");
        }
        // Проверка данных для обновления
        if (location == null || location.getAddress() == null || location.getCity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pickup location data is incomplete");
        }
        return pickupLocationService.update(id, location);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Возвращаем статус 204 (No Content)
    public void deletePickupLocation(@PathVariable Long id) {
        if (!pickupLocationService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found for deletion");
        }
        pickupLocationService.deleteById(id);
    }
}

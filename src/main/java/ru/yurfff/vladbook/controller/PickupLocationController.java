package ru.yurfff.vladbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yurfff.vladbook.model.PickupLocation;
import ru.yurfff.vladbook.service.PickupLocationService;

import java.util.List;
import java.util.Optional;

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
        List<PickupLocation> locations = pickupLocationService.findByCity(city);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pickup locations found for city: " + city);
        }
        return locations;
    }

    @GetMapping("/by-city-postal/{city}/{postalCode}")
    public List<PickupLocation> getPickupLocationsByCityAndPostalCode(@PathVariable String city, @PathVariable String postalCode) {
        List<PickupLocation> locations = pickupLocationService.findByCityAndPostalCode(city, postalCode);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pickup locations found for city and postal code: " + city + ", " + postalCode);
        }
        return locations;
    }

    @GetMapping("/by-postal/{postalCode}")
    public List<PickupLocation> getPickupLocationsByPostalCode(@PathVariable String postalCode) {
        List<PickupLocation> locations = pickupLocationService.findByPostalCode(postalCode);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pickup locations found for postal code: " + postalCode);
        }
        return locations;
    }

    @GetMapping("/by-address/{address}")
    public List<PickupLocation> getPickupLocationsByAddress(@PathVariable String address) {
        List<PickupLocation> locations = pickupLocationService.findByAddress(address);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pickup locations found for address: " + address);
        }
        return locations;
    }

    @PostMapping
    public PickupLocation createPickupLocation(@RequestBody PickupLocation location) {
        return pickupLocationService.save(location);
    }

    @PutMapping("/{id}")
    public PickupLocation updatePickupLocation(@PathVariable Long id, @RequestBody PickupLocation location) {
        if (!pickupLocationService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found for update");
        }
        return pickupLocationService.update(id, location);
    }

    @DeleteMapping("/{id}")
    public void deletePickupLocation(@PathVariable Long id) {
        if (!pickupLocationService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found for deletion");
        }
        pickupLocationService.deleteById(id);
    }
}

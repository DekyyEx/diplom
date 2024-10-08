package ru.yurfff.vladbook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "pickup_locations")
public class PickupLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Address is mandatory")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "City is mandatory")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "^\\d{5,6}$", message = "Invalid postal code format")
    @Column(nullable = false)
    private String postalCode;

    // Можно добавить координаты (например, для отображения на карте)
    // @Column
    // private Double latitude;
    // @Column
    // private Double longitude;
}

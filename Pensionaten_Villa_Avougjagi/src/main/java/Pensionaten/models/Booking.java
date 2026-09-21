package Pensionaten.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

// Entity som representerar en bokning i databasen
@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // En bokning måste alltid kopplas till en kund
    @ManyToOne(optional = false)
    @JoinColumn //FK
    private Customer customer;

    // En bokning måste alltid kopplas till ett rum
    @ManyToOne(optional = false)
    @JoinColumn
    private Room room;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @Min(1)
    private int numberOfGuests;
}
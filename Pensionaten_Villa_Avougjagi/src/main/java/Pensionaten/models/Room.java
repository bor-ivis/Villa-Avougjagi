package Pensionaten.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

// Entity som representerar ett rum i databasen
@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Rumsnummer måste vara unikt så att två rum inte får samma nummer
    @NotBlank
    @Column(nullable = false, unique = true)
    private String roomNumber;

    // Sparar enum-värdet som text i databasen, t.ex. SINGLE eller DOUBLE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Min(0)
    @Max(2)
    private int extraBeds;

    @Min(0)
    @Builder.Default
    private int pricePerNight = 995;

    // Räknar ut total kapacitet utifrån rumstyp och antal extrasängar
    public int getCapacity() {
        return roomType.getBaseCapacity() + extraBeds;
    }
}

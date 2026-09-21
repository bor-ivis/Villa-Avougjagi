package Pensionaten.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

// DTO som används för att skicka bokningsdata mellan controller, service och vyer
@Data
public class BookingDTO {

    private Long id;

    @NotNull(message = "Kund måste väljas")
    private Long customerId;

    @NotNull(message = "Rum måste väljas")
    private Long roomId;

    @NotNull(message = "Incheckningsdatum krävs")
    private LocalDate checkInDate;

    @NotNull(message = "Utcheckningsdatum krävs")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Minst 1 person krävs")
    private int numberOfGuests;

    // Extra fält som används för att visa kundinformation i vyerna
    private String customerFirstName;
    private String customerLastName;

    // Extra fält som används för att visa rumsinformation i vyerna
    private String roomNumber;
    private String roomType;
    private int roomPricePerNight;

    // Uträknade värden som visas i bokningslistan
    private long nights;
    private int totalPrice;
}

package Pensionaten.config;

import Pensionaten.models.Room;
import Pensionaten.models.RoomType;
import Pensionaten.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Körs automatiskt när applikationen startar
// Används för att lägga in några standardrum om databasen är tom
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoomRepository roomRepository;

    @Override
    public void run(String... args) {
        // Om rum redan finns i databasen ska inga nya rum skapas
        if (roomRepository.count() > 0) {
            return;
        }

        // Skapar ett enkelrum
        roomRepository.save(Room.builder()
                .roomNumber("101")
                .roomType(RoomType.SINGLE)
                .extraBeds(0)
                .pricePerNight(995)
                .build());

        // Skapar ett dubbelrum med en extra säng
        roomRepository.save(Room.builder()
                .roomNumber("201")
                .roomType(RoomType.DOUBLE)
                .extraBeds(1)
                .pricePerNight(1495)
                .build());

        // Skapar ett större dubbelrum med två extrasängar
        roomRepository.save(Room.builder()
                .roomNumber("301")
                .roomType(RoomType.DOUBLE)
                .extraBeds(2)
                .pricePerNight(1895)
                .build());
    }
}

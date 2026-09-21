package Pensionaten.service;

import Pensionaten.dto.RoomDTO;
import Pensionaten.models.Room;
import Pensionaten.models.RoomType;
import Pensionaten.repositories.BookingRepository;
import Pensionaten.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

// Serviceklass som hanterar rum, kapacitet och sökning av lediga rum
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    // Hämtar alla rum och gör om dem till DTO
    public List<RoomDTO> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Hämtar ett rum baserat på id
    public RoomDTO findById(Long id) {
        return roomRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    // Sparar nytt rum eller uppdaterar befintligt rum
    public RoomDTO save(RoomDTO dto) {
       boolean roomNumberTaken;

       // Om rummet är nytt kontrolleras om rumsnumret redan finns
        if (dto.getId() == null) {
            roomNumberTaken = roomRepository.existsByRoomNumber(dto.getRoomNumber());
        } else {
            // Vid redigering kontrolleras att inget annat rum har samma rumsnummer
            roomNumberTaken = roomRepository.existsByRoomNumberAndIdNot(dto.getRoomNumber(), dto.getId());
        }
        // Stoppar sparningen om rumsnumret redan används
        if (roomNumberTaken) {
            return null;
        }
        Room savedRoom = roomRepository.save(toEntity(dto));
        return toDTO(savedRoom);
    }

    // Tar bort rum endast om rummet inte har bokningar
    public boolean delete(Long id) {
        if (!roomRepository.existsById(id)) {
            return false;
        }
        if (bookingRepository.existsByRoomId(id)) {
            return false;
        }
        roomRepository.deleteById(id);
        return true;
    }

    // Söker lediga rum baserat på datum och antal gäster
    public List<RoomDTO> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, int guests, Long bookingId) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn) || guests < 1) {
            return List.of();
        }

        return roomRepository.findAvailableRooms(checkIn, checkOut, guests, bookingId)
                .stream()
                // Kontrollerar datumkonflikter och behåller affärslogiken för kapacitet
                .filter(room -> room.getCapacity() >= guests)
                .map(this::toDTO)
                .toList();
    }

    // Kontrollerar om ett specifikt rum finns bland de lediga rummen
    public boolean isRoomAvailable(Long roomId,
                                   LocalDate checkIn,
                                   LocalDate checkOut,
                                   int guests,
                                   Long bookingId) {

        if (roomId == null) {
            return false;
        }

        return findAvailableRooms(checkIn, checkOut, guests, bookingId)
                .stream()
                .anyMatch(room -> room.getId().equals(roomId));
    }

    private RoomDTO toDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType().name())
                .extraBeds(room.getExtraBeds())
                .pricePerNight(room.getPricePerNight())
                .capacity(room.getCapacity())
                .build();
    }

    private Room toEntity(RoomDTO dto) {
        RoomType type = RoomType.valueOf(dto.getRoomType());
        int extraBeds = type == RoomType.SINGLE ? 0 : dto.getExtraBeds();
        int price = dto.getPricePerNight() > 0 ? dto.getPricePerNight() : defaultPrice(type, extraBeds);

        return Room.builder()
                .id(dto.getId())
                .roomNumber(dto.getRoomNumber())
                .roomType(type)
                .extraBeds(extraBeds)
                .pricePerNight(price)
                .build();
    }

    private int defaultPrice(RoomType type, int extraBeds) {
        return type == RoomType.SINGLE ? 995 : 1495 + (extraBeds * 250);
    }
}

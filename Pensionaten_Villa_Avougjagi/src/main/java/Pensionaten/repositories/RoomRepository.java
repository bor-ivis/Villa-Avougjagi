package Pensionaten.repositories;

import Pensionaten.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

// Repository för rum och sökning av lediga rum
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByRoomNumber(String roomNumber);

    boolean existsByRoomNumberAndIdNot(String roomNumber, Long id);

    // Hämtar rum som inte har överlappande bokningar under valt datumintervall
    @Query("""
        SELECT r FROM Room r
        WHERE :guests > 0
        AND r.id NOT IN (
            SELECT b.room.id FROM Booking b
            WHERE b.checkInDate < :checkOutDate
            AND b.checkOutDate > :checkInDate
            AND (:bookingId IS NULL OR b.id <> :bookingId)
        )
    """)
    List<Room> findAvailableRooms(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("guests") int guests,
            @Param("bookingId") Long bookingId
    );
}
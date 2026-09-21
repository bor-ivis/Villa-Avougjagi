/*package Pensionaten.service;

import Pensionaten.dto.RoomDTO;
import Pensionaten.models.Room;
import Pensionaten.models.RoomType;
import Pensionaten.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    void findAll_shouldReturnRoomDTOList() {
        Room room = Room.builder()
                .id(1L)
                .roomNumber("101")
                .roomType(RoomType.SINGLE)
                .extraBeds(0)
                .build();

        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<RoomDTO> result = roomService.findAll();

        assertEquals(1, result.size());
        assertEquals("101", result.get(0).getRoomNumber());
        assertEquals("SINGLE", result.get(0).getRoomType());
    }

    @Test
    void findById_shouldReturnRoomDTO_whenRoomExists() {
        Room room = Room.builder()
                .id(1L)
                .roomNumber("101")
                .roomType(RoomType.SINGLE)
                .extraBeds(0)
                .build();

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        RoomDTO result = roomService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    void findById_shouldThrowException_whenRoomDoesNotExist() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.findById(99L));
    }

    @Test
    void save_shouldSaveRoomAndReturnDTO() {
        RoomDTO dto = RoomDTO.builder()
                .id(1L)
                .roomNumber("101")
                .roomType("SINGLE")
                .extraBeds(0)
                .build();

        Room savedRoom = Room.builder()
                .id(1L)
                .roomNumber("101")
                .roomType(RoomType.SINGLE)
                .extraBeds(0)
                .build();

        when(roomRepository.save(any(Room.class))).thenReturn(savedRoom);

        RoomDTO result = roomService.save(dto);

        assertEquals("101", result.getRoomNumber());
        assertEquals("SINGLE", result.getRoomType());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void delete_shouldDeleteRoomById() {
        roomService.delete(1L);

        verify(roomRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAvailableRooms_shouldReturnAvailableRooms() {
        LocalDate checkIn = LocalDate.of(2026, 6, 1);
        LocalDate checkOut = LocalDate.of(2026, 6, 3);

        Room room = Room.builder()
                .id(1L)
                .roomNumber("201")
                .roomType(RoomType.DOUBLE)
                .extraBeds(1)
                .build();

        when(roomRepository.findAvailableRooms(checkIn, checkOut, 3, null))
                .thenReturn(List.of(room));

        List<RoomDTO> result = roomService.findAvailableRooms(checkIn, checkOut, 3, null);

        assertEquals(1, result.size());
        assertEquals("201", result.get(0).getRoomNumber());
    }

    @Test
    void isRoomAvailable_shouldReturnTrue_whenRoomIsAvailable() {
        LocalDate checkIn = LocalDate.of(2026, 6, 1);
        LocalDate checkOut = LocalDate.of(2026, 6, 3);

        Room room = Room.builder()
                .id(1L)
                .roomNumber("201")
                .roomType(RoomType.DOUBLE)
                .extraBeds(1)
                .build();

        when(roomRepository.findAvailableRooms(checkIn, checkOut, 3, null))
                .thenReturn(List.of(room));

        boolean result = roomService.isRoomAvailable(1L, checkIn, checkOut, 3, null);

        assertTrue(result);
    }

    @Test
    void isRoomAvailable_shouldReturnFalse_whenRoomIsNotAvailable() {
        LocalDate checkIn = LocalDate.of(2026, 6, 1);
        LocalDate checkOut = LocalDate.of(2026, 6, 3);

        when(roomRepository.findAvailableRooms(checkIn, checkOut, 3, null))
                .thenReturn(List.of());

        boolean result = roomService.isRoomAvailable(1L, checkIn, checkOut, 3, null);

        assertFalse(result);
    }
}*/
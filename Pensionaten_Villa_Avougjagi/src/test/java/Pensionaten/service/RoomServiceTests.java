package Pensionaten.service;

import Pensionaten.dto.RoomDTO;
import Pensionaten.models.Room;
import Pensionaten.models.RoomType;
import Pensionaten.repositories.BookingRepository;
import Pensionaten.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;

    private RoomDTO dto;

    @BeforeEach
    void setUp() {

        room = Room.builder()
                .id(1L)
                .roomNumber("101")
                .roomType(RoomType.SINGLE)
                .extraBeds(0)
                .pricePerNight(995)
                .build();

        dto = RoomDTO.builder()
                .roomNumber("101")
                .roomType("SINGLE")
                .extraBeds(0)
                .pricePerNight(995)
                .build();

    }

    @Test
    void findAll_shouldReturnRoomDTOList() {

        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<RoomDTO> result = roomService.findAll();

        assertEquals(1, result.size());
        assertEquals("101", result.get(0).getRoomNumber());
        assertEquals("SINGLE", result.get(0).getRoomType());
    }

    @Test
    void findById_shouldReturnRoomDTO_whenRoomExists() {

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        RoomDTO result = roomService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    void findById_shouldReturnNull_whenRoomDoesNotExist() {

        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        RoomDTO result = roomService.findById(99L);

        assertNull(result);
    }

    @Test
    void save_shouldReturnNull_whenRoomNumberAlreadyExists_newRoom() {

        when(roomRepository.existsByRoomNumber(dto.getRoomNumber())).thenReturn(true);

        RoomDTO result = roomService.save(dto);

        assertNull(result);
        verify(roomRepository, never()).save(any());
    }

    @Test
    void save_shouldReturnSavedRoom_whenValidNewRoom() {

        RoomDTO dto = RoomDTO.builder()
                .roomNumber("101")
                .roomType("SINGLE")
                .extraBeds(0)
                .pricePerNight(995)
                .build();

        when(roomRepository.existsByRoomNumber(dto.getRoomNumber())).thenReturn(false);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomDTO result = roomService.save(dto);

        assertNotNull(result);
        assertEquals("101", result.getRoomNumber());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void delete_shouldReturnFalse_whenRoomDoesNotExist() {

        when(roomRepository.existsById(1L)).thenReturn(false);

        boolean result = roomService.delete(1L);

        assertFalse(result);
        verify(roomRepository, never()).deleteById(1L);
    }

    @Test
    void delete_shouldReturnFalse_whenRoomHasBookings() {

        when(roomRepository.existsById(1L)).thenReturn(true);
        when(bookingRepository.existsByRoomId(1L)).thenReturn(true);

        boolean result = roomService.delete(1L);

        assertFalse(result);
        verify(roomRepository, never()).deleteById(1L);
    }

    @Test
    void delete_shouldReturnTrue_whenRoomCanBeDeleted() {

        when(roomRepository.existsById(1L)).thenReturn(true);
        when(bookingRepository.existsByRoomId(1L)).thenReturn(false);

        boolean result = roomService.delete(1L);

        assertTrue(result);
        verify(roomRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAvailableRooms_shouldReturnEmpty_whenInvalidDates() {

        List<RoomDTO> result = roomService.findAvailableRooms(
                LocalDate.of(2026, 6, 3),
                LocalDate.of(2026, 6, 1),
                1,
                null
        );

        assertTrue(result.isEmpty());
    }

    @Test
    void isRoomAvailable_shouldReturnFalse_whenRoomIdIsNull() {

        boolean result = roomService.isRoomAvailable(
                null,
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                1,
                null
        );

        assertFalse(result);
    }

    @Test
    void isRoomAvailable_shouldReturnTrue_whenRoomExistsInAvailableList() {

        RoomDTO dto = RoomDTO.builder()
                .id(1L)
                .roomNumber("101")
                .roomType("SINGLE")
                .capacity(1)
                .build();

        RoomService availableService = spy(roomService);

        doReturn(List.of(dto))
                .when(availableService)
                .findAvailableRooms(any(), any(), anyInt(), any());

        boolean result = availableService.isRoomAvailable(
                1L,
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                1,
                null
        );

        assertTrue(result);
    }
}
package ru.neoflex.autoplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.AnalyticsSnapshotService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AnalyticsSnapshotControllerTest {

    private MockMvc mock;

    @Mock
    private AnalyticsSnapshotService service;

    @InjectMocks
    private AnalyticsSnapshotController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mock = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private static final String url = "/api/v1/analytics_snapshots";

    @Test
    void getByUserIdSuccess() throws Exception{
        List<AnalyticsSnapshotResponseDto> snapshots = List.of(
                new AnalyticsSnapshotResponseDto(1L, 10L, 2, new BigDecimal("100.0"), 3,
                        5L, LocalDateTime.now(), LocalDateTime.now())
        );

        when(service.getByUserId(10L)).thenReturn(snapshots);

        mock.perform(get(url).param("user_id", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].userId").value(10));

        verify(service).getByUserId(10L);

    }

//    @Test
//    void getByUserIdNotFound() {
//        Long userId = 99L;
//
//        when(service.getByUserId(userId)).thenThrow(new NoSuchElementException("User snapshots not found"));
//
//        Exception exception = assertThrows(NoSuchElementException.class, () -> {
//            controller.getByUserId(userId);
//        });
//
//        assertEquals("User snapshots not found", exception.getMessage());
//        verify(service).getByUserId(userId);
//    }
//
//    @Test
//    void createSuccess() {
//        AnalyticsSnapshotRequestDto requestDto = new AnalyticsSnapshotRequestDto(1L, 5,
//                new BigDecimal("200.0"), 2, 3L);
//        AnalyticsSnapshotResponseDto responseDto = new AnalyticsSnapshotResponseDto(1L, 1L,
//                5, new BigDecimal("200.0"), 2, 3L, LocalDateTime.now(), LocalDateTime.now());
//
//        when(service.create(requestDto)).thenReturn(responseDto);
//
//        ResponseEntity<ApiResponseDto<AnalyticsSnapshotResponseDto>> response = controller.create(requestDto);
//
//        assertEquals(201, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(responseDto.getId(), response.getBody().getData().getId());
//        verify(service).create(requestDto);
//    }
//
//    @Test
//    void createInvalidRequest() {
//        AnalyticsSnapshotRequestDto invalidDto = new AnalyticsSnapshotRequestDto(1L, 5, null, 2, 3L);
//
//        when(service.create(invalidDto)).thenThrow(new IllegalArgumentException("totalSpent is required"));
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.create(invalidDto);
//        });
//
//        assertEquals("totalSpent is required", exception.getMessage());
//        verify(service).create(invalidDto);
//    }
//
//    @Test
//    void createUserNotFound() {
//        AnalyticsSnapshotRequestDto dto = new AnalyticsSnapshotRequestDto(1L, 5,
//                new BigDecimal("200.0"), 2, 3L);
//
//        when(service.create(dto)).thenThrow(new NoSuchElementException("User not found"));
//
//        Exception exception = assertThrows(NoSuchElementException.class, () -> {
//            controller.create(dto);
//        });
//
//        assertEquals("User not found", exception.getMessage());
//        verify(service).create(dto);
//    }
//
//    @Test
//    void updateSuccess() {
//        Long snapshotId = 1L;
//        AnalyticsSnapshotUpdateDto updateDto = new AnalyticsSnapshotUpdateDto(new BigDecimal("250.0"), 3, 4L);
//        AnalyticsSnapshotResponseDto responseDto = new AnalyticsSnapshotResponseDto(snapshotId, 1L, 5,
//                new BigDecimal("250.0"), 3, 4L, LocalDateTime.now(), LocalDateTime.now());
//
//        when(service.update(snapshotId, updateDto)).thenReturn(responseDto);
//
//        ResponseEntity<ApiResponseDto<AnalyticsSnapshotResponseDto>> response = controller.update(snapshotId, updateDto);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(snapshotId, response.getBody().getData().getId());
//        verify(service).update(snapshotId, updateDto);
//    }
//
//    @Test
//    void updateNotFound() {
//        Long snapshotId = 99L;
//        AnalyticsSnapshotUpdateDto updateDto = new AnalyticsSnapshotUpdateDto(new BigDecimal("250.0"), 3, 4L);
//
//        when(service.update(snapshotId, updateDto)).thenThrow(new NoSuchElementException("Snapshot not found"));
//
//        Exception exception = assertThrows(NoSuchElementException.class, () -> {
//            controller.update(snapshotId, updateDto);
//        });
//
//        assertEquals("Snapshot not found", exception.getMessage());
//        verify(service).update(snapshotId, updateDto);
//    }
//
//    @Test
//    void updateInvalidRequest() {
//        Long snapshotId = 1L;
//        AnalyticsSnapshotUpdateDto invalidDto = new AnalyticsSnapshotUpdateDto(null, -1, null);
//
//        when(service.update(snapshotId, invalidDto)).thenThrow(new IllegalArgumentException("Invalid update data"));
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.update(snapshotId, invalidDto);
//        });
//
//        assertEquals("Invalid update data", exception.getMessage());
//        verify(service).update(snapshotId, invalidDto);
//    }
//
//    @Test
//    void deleteSuccess() {
//        Long snapshotId = 1L;
//
//        doNothing().when(service).delete(snapshotId);
//
//        ResponseEntity<ApiResponseDto<String>> response = controller.delete(snapshotId);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals("Analytics snapshot deleted successfully", response.getBody().getMessage());
//        verify(service).delete(snapshotId);
//    }
//
//    @Test
//    void deleteNotFound() {
//        Long snapshotId = 99L;
//
//        doThrow(new NoSuchElementException("Snapshot not found")).when(service).delete(snapshotId);
//
//        Exception exception = assertThrows(NoSuchElementException.class, () -> {
//            controller.delete(snapshotId);
//        });
//
//        assertEquals("Snapshot not found", exception.getMessage());
//        verify(service).delete(snapshotId);
//    }
//
//    @Test
//    void deleteInternalServerError() {
//        Long snapshotId = 1L;
//
//        doThrow(new RuntimeException("Internal server error")).when(service).delete(snapshotId);
//
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            controller.delete(snapshotId);
//        });
//
//        assertEquals("Internal server error", exception.getMessage());
//        verify(service).delete(snapshotId);
//    }
}
package ru.neoflex.autoplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.autoplanner.dto.ServiceCenterRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterResponseDto;
import ru.neoflex.autoplanner.dto.ServiceCenterUpdateRequestDto;
import ru.neoflex.autoplanner.entity.ServiceCenter;
import ru.neoflex.autoplanner.mapper.ServiceCenterMapper;
import ru.neoflex.autoplanner.repository.ServiceCenterRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCenterServiceTest {

    @Mock
    private ServiceCenterRepository repository;
    @Mock
    private ServiceCenterMapper mapper;

    @InjectMocks
    private ServiceCenterService service;

    private ServiceCenter center;
    private ServiceCenterResponseDto responseDto;

    @BeforeEach
    void setUp() {
        center = new ServiceCenter();
        center.setId(1L);
        center.setName("Test Center");
        center.setAddress("123 Main St");
        center.setPhone("1234567890");
        center.setRating(BigDecimal.valueOf(4.5));

        responseDto = new ServiceCenterResponseDto();
    }

    @Test
    void getByIdSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(center));
        when(mapper.toDto(center)).thenReturn(responseDto);

        ServiceCenterResponseDto result = service.getById(1L);

        assertNotNull(result);
        verify(repository).findById(1L);
        verify(mapper).toDto(center);
    }

    @Test
    void getByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.getById(1L));
    }

    @Test
    void createSuccess() {
        ServiceCenterRequestDto dto = new ServiceCenterRequestDto();
        dto.setName("Test Center");
        dto.setAddress("123 Main St");

        when(repository.findByNameContainingIgnoreCase("Test Center")).thenReturn(Collections.emptyList());
        when(mapper.toEntity(dto)).thenReturn(center);
        when(repository.save(center)).thenReturn(center);
        when(mapper.toDto(center)).thenReturn(responseDto);

        ServiceCenterResponseDto result = service.create(dto);

        assertNotNull(result);
        verify(repository).save(center);
    }

    @Test
    void createDuplicateThrowsException() {
        ServiceCenterRequestDto dto = new ServiceCenterRequestDto();
        dto.setName("Test Center");
        dto.setAddress("123 Main St");

        when(repository.findByNameContainingIgnoreCase("Test Center")).thenReturn(List.of(center));

        assertThrows(IllegalStateException.class,
                () -> service.create(dto));
    }

    @Test
    void updateSuccess() {
        ServiceCenterUpdateRequestDto dto = new ServiceCenterUpdateRequestDto();
        dto.setName("Updated Center");
        dto.setAddress("456 New St");

        when(repository.findById(1L)).thenReturn(Optional.of(center));
        doNothing().when(mapper).updateFromDto(dto, center);
        when(repository.save(center)).thenReturn(center);
        when(mapper.toDto(center)).thenReturn(responseDto);

        ServiceCenterResponseDto result = service.update(1L, dto);

        assertNotNull(result);
        verify(repository).save(center);
        verify(mapper).updateFromDto(dto, center);
    }

    @Test
    void updateNotFound() {
        ServiceCenterUpdateRequestDto dto = new ServiceCenterUpdateRequestDto();
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.update(1L, dto));
    }

    @Test
    void deleteSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(center));

        service.delete(1L);

        verify(repository).delete(center);
    }

    @Test
    void deleteNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.delete(1L));
    }
}
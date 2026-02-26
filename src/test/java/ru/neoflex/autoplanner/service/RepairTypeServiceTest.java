package ru.neoflex.autoplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.autoplanner.dto.RepairTypeRequestDto;
import ru.neoflex.autoplanner.dto.RepairTypeResponseDto;
import ru.neoflex.autoplanner.entity.RepairType;
import ru.neoflex.autoplanner.mapper.RepairTypeMapper;
import ru.neoflex.autoplanner.repository.RepairTypeRepository;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RepairTypeServiceTest {

    @Mock
    private RepairTypeRepository repository;

    @Mock
    private RepairTypeMapper mapper;

    @InjectMocks
    private RepairTypeService service;

    private RepairType repairType;
    private RepairTypeResponseDto responseDto;

    @BeforeEach
    void setUp() {
        repairType = new RepairType();
        repairType.setId(1L);
        repairType.setName("Oil Change");
        repairType.setCategory("Maintenance");

        responseDto = new RepairTypeResponseDto();
    }

    @Test
    void getAllSuccess() {
        when(repository.findAll()).thenReturn(List.of(repairType));
        when(mapper.toDto(repairType)).thenReturn(responseDto);

        List<RepairTypeResponseDto> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
        verify(mapper).toDto(repairType);
    }

    @Test
    void getAllEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class,
                () -> service.getAll());
    }

    @Test
    void createSuccess() {
        RepairTypeRequestDto dto = new RepairTypeRequestDto();
        dto.setName("Brake Repair");
        dto.setCategory("Repair");

        when(repository.save(any(RepairType.class)))
                .thenReturn(repairType);
        when(mapper.toDto(repairType))
                .thenReturn(responseDto);

        RepairTypeResponseDto result = service.create(dto);

        assertNotNull(result);
        verify(repository).save(any(RepairType.class));
    }

    @Test
    void updateSuccess() {
        RepairTypeRequestDto dto = new RepairTypeRequestDto();
        dto.setName("Updated Name");
        dto.setCategory("Updated Category");

        when(repository.findById(1L))
                .thenReturn(Optional.of(repairType));
        when(repository.save(repairType))
                .thenReturn(repairType);
        when(mapper.toDto(repairType))
                .thenReturn(responseDto);

        RepairTypeResponseDto result =
                service.update(1L, dto);

        assertNotNull(result);
        verify(repository).save(repairType);
    }

    @Test
    void updateNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.update(1L, new RepairTypeRequestDto()));
    }

    @Test
    void deleteSuccess() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(repairType));

        service.delete(1L);

        verify(repository).delete(repairType);
    }

    @Test
    void deleteNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.delete(1L));
    }
}
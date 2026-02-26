package ru.neoflex.autoplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeResponseDto;
import ru.neoflex.autoplanner.entity.RepairType;
import ru.neoflex.autoplanner.entity.ServiceCenter;
import ru.neoflex.autoplanner.entity.ServiceCenterRepairType;
import ru.neoflex.autoplanner.mapper.ServiceCenterRepairTypeMapper;
import ru.neoflex.autoplanner.repository.RepairTypeRepository;
import ru.neoflex.autoplanner.repository.ServiceCenterRepairTypeRepository;
import ru.neoflex.autoplanner.repository.ServiceCenterRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ServiceCenterRepairTypeServiceTest {

    @Mock
    private ServiceCenterRepairTypeRepository repository;
    @Mock
    private ServiceCenterRepository serviceCenterRepository;
    @Mock
    private RepairTypeRepository repairTypeRepository;
    @Mock
    private ServiceCenterRepairTypeMapper mapper;

    @InjectMocks
    private ServiceCenterRepairTypeService service;

    private ServiceCenter serviceCenter;
    private RepairType repairType;
    private ServiceCenterRepairType entity;
    private ServiceCenterRepairTypeResponseDto responseDto;

    @BeforeEach
    void setUp() {
        serviceCenter = new ServiceCenter();
        serviceCenter.setId(1L);

        repairType = new RepairType();
        repairType.setId(2L);

        entity = new ServiceCenterRepairType();
        entity.setId(10L);
        entity.setServiceCenter(serviceCenter);
        entity.setRepairType(repairType);
        entity.setPrice(100);
        entity.setCurrency("USD");

        responseDto = new ServiceCenterRepairTypeResponseDto();
    }

    @Test
    void getByServiceCenterSuccess() {
        when(repository.findByServiceCenter_Id(1L)).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(responseDto);

        List<ServiceCenterRepairTypeResponseDto> result = service.getByServiceCenter(1L);

        assertEquals(1, result.size());
        verify(repository).findByServiceCenter_Id(1L);
        verify(mapper).toDto(entity);
    }

    @Test
    void getByServiceCenterEmpty() {
        when(repository.findByServiceCenter_Id(1L)).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class,
                () -> service.getByServiceCenter(1L));
    }

    @Test
    void addSuccess() {
        ServiceCenterRepairTypeRequestDto dto = new ServiceCenterRepairTypeRequestDto();
        dto.setServiceCenterId(1L);
        dto.setRepairTypeId(2L);

        when(repository.findByServiceCenter_IdAndRepairType_Id(1L, 2L)).thenReturn(Optional.empty());
        when(serviceCenterRepository.findById(1L)).thenReturn(Optional.of(serviceCenter));
        when(repairTypeRepository.findById(2L)).thenReturn(Optional.of(repairType));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(responseDto);

        ServiceCenterRepairTypeResponseDto result = service.add(dto);

        assertNotNull(result);
        verify(repository).save(entity);
    }

    @Test
    void addDuplicateThrowsException() {
        ServiceCenterRepairTypeRequestDto dto = new ServiceCenterRepairTypeRequestDto();
        dto.setServiceCenterId(1L);
        dto.setRepairTypeId(2L);

        when(repository.findByServiceCenter_IdAndRepairType_Id(1L, 2L))
                .thenReturn(Optional.of(entity));

        assertThrows(IllegalStateException.class,
                () -> service.add(dto));
    }

    @Test
    void addServiceCenterNotFound() {
        ServiceCenterRepairTypeRequestDto dto = new ServiceCenterRepairTypeRequestDto();
        dto.setServiceCenterId(1L);
        dto.setRepairTypeId(2L);

        when(repository.findByServiceCenter_IdAndRepairType_Id(1L, 2L))
                .thenReturn(Optional.empty());
        when(serviceCenterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.add(dto));
    }

    @Test
    void addRepairTypeNotFound() {
        ServiceCenterRepairTypeRequestDto dto = new ServiceCenterRepairTypeRequestDto();
        dto.setServiceCenterId(1L);
        dto.setRepairTypeId(2L);

        when(repository.findByServiceCenter_IdAndRepairType_Id(1L, 2L))
                .thenReturn(Optional.empty());
        when(serviceCenterRepository.findById(1L)).thenReturn(Optional.of(serviceCenter));
        when(repairTypeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.add(dto));
    }

    @Test
    void addInvalidDtoThrowsException() {
        ServiceCenterRepairTypeRequestDto dto = new ServiceCenterRepairTypeRequestDto();
        dto.setServiceCenterId(null);
        dto.setRepairTypeId(2L);

        assertThrows(IllegalArgumentException.class,
                () -> service.add(dto));

        dto.setServiceCenterId(1L);
        dto.setRepairTypeId(null);

        assertThrows(IllegalArgumentException.class,
                () -> service.add(dto));
    }

    @Test
    void deleteSuccess() {
        when(repository.findById(10L)).thenReturn(Optional.of(entity));

        service.delete(10L);

        verify(repository).delete(entity);
    }

    @Test
    void deleteNotFound() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.delete(10L));
    }
}
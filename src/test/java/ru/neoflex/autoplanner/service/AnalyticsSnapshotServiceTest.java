package ru.neoflex.autoplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotRequestDto;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotResponseDto;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotUpdateDto;
import ru.neoflex.autoplanner.entity.AnalyticsSnapshot;
import ru.neoflex.autoplanner.entity.RepairType;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.AnalyticsSnapshotMapper;
import ru.neoflex.autoplanner.repository.AnalyticsSnapshotRepository;
import ru.neoflex.autoplanner.repository.RepairTypeRepository;
import ru.neoflex.autoplanner.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
    class AnalyticsSnapshotServiceTest {

        @Mock
        private AnalyticsSnapshotRepository repository;
        @Mock
        private UserRepository userRepository;
        @Mock
        private RepairTypeRepository repairTypeRepository;
        @Mock
        private AnalyticsSnapshotMapper mapper;

        @InjectMocks
        private AnalyticsSnapshotService service;

        private User user;
        private RepairType repairType;
        private AnalyticsSnapshot snapshot;
        private AnalyticsSnapshotResponseDto responseDto;

        @BeforeEach
        void setUp() {
            user = new User();
            user.setId(1L);

            repairType = new RepairType();
            repairType.setId(2L);

            snapshot = new AnalyticsSnapshot();
            snapshot.setId(10L);
            snapshot.setUser(user);
            snapshot.setMonth(5);
            snapshot.setTotalSpent(BigDecimal.valueOf(1000));
            snapshot.setServiceCount(3);
            snapshot.setMostCommonRepairType(repairType);

            responseDto = new AnalyticsSnapshotResponseDto();
        }

        @Test
        void getByUserIdSuccess() {
            when(repository.findByUserId(1L)).thenReturn(List.of(snapshot));
            when(mapper.toDto(snapshot)).thenReturn(responseDto);

            List<AnalyticsSnapshotResponseDto> result = service.getByUserId(1L);

            assertEquals(1, result.size());
            verify(repository).findByUserId(1L);
        }

        @Test
        void getByUserIdNullUserId() {
            assertThrows(IllegalArgumentException.class,
                    () -> service.getByUserId(null));
        }

        @Test
        void getByUserIdEmptyList() {
            when(repository.findByUserId(1L)).thenReturn(Collections.emptyList());

            assertThrows(NoSuchElementException.class,
                    () -> service.getByUserId(1L));
        }

        @Test
        void createSuccess() {
            AnalyticsSnapshotRequestDto dto = new AnalyticsSnapshotRequestDto();
            dto.setUserId(1L);
            dto.setMostCommonRepairTypeId(2L);
            dto.setMonth(5);
            dto.setTotalSpent(BigDecimal.valueOf(1000));
            dto.setServiceCount(3);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(repairTypeRepository.findById(2L)).thenReturn(Optional.of(repairType));
            when(repository.save(any(AnalyticsSnapshot.class)))
                    .thenReturn(snapshot);
            when(mapper.toDto(snapshot)).thenReturn(responseDto);

            AnalyticsSnapshotResponseDto result = service.create(dto);

            assertNotNull(result);
            verify(repository).save(any(AnalyticsSnapshot.class));
        }

        @Test
        void createUserNotFound() {
            AnalyticsSnapshotRequestDto dto = new AnalyticsSnapshotRequestDto();
            dto.setUserId(1L);

            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(NoSuchElementException.class,
                    () -> service.create(dto));
        }


        @Test
        void updateSuccess() {
            AnalyticsSnapshotUpdateDto dto = new AnalyticsSnapshotUpdateDto();
            dto.setTotalSpent(BigDecimal.valueOf(2000));
            dto.setServiceCount(5);
            dto.setMostCommonRepairTypeId(2L);

            when(repository.findById(10L)).thenReturn(Optional.of(snapshot));
            when(repairTypeRepository.findById(2L)).thenReturn(Optional.of(repairType));
            when(repository.save(snapshot)).thenReturn(snapshot);
            when(mapper.toDto(snapshot)).thenReturn(responseDto);

            AnalyticsSnapshotResponseDto result = service.update(10L, dto);

            assertNotNull(result);
            verify(repository).save(snapshot);
        }

        @Test
        void updateNotFound() {
            when(repository.findById(10L)).thenReturn(Optional.empty());

            assertThrows(NoSuchElementException.class,
                    () -> service.update(10L, new AnalyticsSnapshotUpdateDto()));
        }


        @Test
        void deleteSuccess() {
            when(repository.findById(10L)).thenReturn(Optional.of(snapshot));

            service.delete(10L);

            verify(repository).delete(snapshot);
        }

        @Test
        void deleteNotFound() {
            when(repository.findById(10L)).thenReturn(Optional.empty());

            assertThrows(NoSuchElementException.class,
                    () -> service.delete(10L));
        }
}

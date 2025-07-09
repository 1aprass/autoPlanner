package ru.neoflex.autoplanner.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.entity.Reminder;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.ReminderMapper;
import ru.neoflex.autoplanner.repository.ReminderRepository;
import ru.neoflex.autoplanner.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final ReminderMapper mapper;

    @Transactional(readOnly = true)
    public List<ReminderResponseDto> getAllRemindersByUser(Long userId) {
        List<Reminder> reminders = reminderRepository.findAllByUserId(userId);
        if (reminders.isEmpty()) {
            throw new EntityNotFoundException("Reminders not found for user with id: " + userId);
        }
        return reminders.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ReminderResponseDto createReminder(ReminderRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        Reminder reminder = mapper.toEntity(dto);
        reminder.setUser(user);
        return mapper.toDto(reminderRepository.save(reminder));
    }

    @Transactional
    public ReminderResponseDto updateReminder(Long id, ReminderUpdateRequestDto dto) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found with id: " + id));

        mapper.updateEntityFromDto(reminder, dto);
        return mapper.toDto(reminderRepository.save(reminder));
    }

    @Transactional
    public void deleteReminder(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found with id: " + id));
        reminderRepository.delete(reminder);
    }
}
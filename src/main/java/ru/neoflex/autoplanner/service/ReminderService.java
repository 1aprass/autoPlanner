package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public List<ReminderResponseDto> getAllRemindersByUser(Long userId) {
        List<Reminder> reminders = reminderRepository.findAllByUserId(userId);
        if (reminders.isEmpty()) {
            throw new RuntimeException("Reminders not found for user with id: " + userId);
        }
        return reminders.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ReminderResponseDto createReminder(ReminderRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        Reminder reminder = mapper.toEntity(dto);
        reminder.setUser(user);
        return mapper.toDto(reminderRepository.save(reminder));
    }

    public ReminderResponseDto updateReminder(Long id, ReminderUpdateRequestDto dto) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reminder not found with id: " + id));

        mapper.updateEntityFromDto(reminder, dto);
        return mapper.toDto(reminderRepository.save(reminder));
    }

    public void deleteReminder(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reminder not found with id: " + id));
        reminderRepository.delete(reminder);
    }
}
package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.ReminderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reminders")
@RequiredArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ReminderResponseDto>>> getReminders(
            @RequestParam(value = "user_id", required = false) Long userId) {

        List<ReminderResponseDto> reminders = reminderService.getAllRemindersByUser(userId);
        return ResponseEntity.ok(ApiResponseDto.success(reminders));

    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ReminderResponseDto>> createReminder(@Valid @RequestBody ReminderRequestDto dto) {

        ReminderResponseDto reminder = reminderService.createReminder(dto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success("Reminder added successfully", reminder));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ReminderResponseDto>> updateReminder(
            @Valid @PathVariable Long id, @RequestBody ReminderUpdateRequestDto dto) {

        ReminderResponseDto updated = reminderService.updateReminder(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Reminder updated successfully", updated));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteReminder(@PathVariable Long id) {

        reminderService.deleteReminder(id);
        return ResponseEntity.ok(ApiResponseDto.success("Reminder deleted successfully"));

    }
}
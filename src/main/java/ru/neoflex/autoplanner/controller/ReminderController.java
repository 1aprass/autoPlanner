package ru.neoflex.autoplanner.controller;

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
    public ResponseEntity<?> getReminders(@RequestParam(value = "user_id", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error("Missing required parameter: user_id"));
        }
        try {
            List<ReminderResponseDto> reminders = reminderService.getAllRemindersByUser(userId);
            return ResponseEntity.ok(ApiResponseDto.success(reminders));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createReminder(@RequestBody ReminderRequestDto dto) {
        try {
            ReminderResponseDto reminder = reminderService.createReminder(dto);
            return ResponseEntity.status(201)
                    .body(ApiResponseDto.success("Reminder added successfully", reminder));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable Long id, @RequestBody ReminderUpdateRequestDto dto) {
        try {
            ReminderResponseDto updated = reminderService.updateReminder(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Reminder updated successfully", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable Long id) {
        try {
            reminderService.deleteReminder(id);
            return ResponseEntity.ok(ApiResponseDto.success("Reminder deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }
}
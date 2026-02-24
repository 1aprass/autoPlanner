package ru.neoflex.autoplanner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all user reminders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminders received successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "No reminders found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ReminderResponseDto>>> getReminders(
            @RequestParam(value = "user_id", required = false) Long userId) {

        List<ReminderResponseDto> reminders = reminderService.getAllRemindersByUser(userId);
        return ResponseEntity.ok(ApiResponseDto.success(reminders));

    }

    @Operation(summary = "Adding a new reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reminder added successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect data in the request",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<ReminderResponseDto>> createReminder(@Valid @RequestBody ReminderRequestDto dto) {

        ReminderResponseDto reminder = reminderService.createReminder(dto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success("Reminder added successfully", reminder));

    }

    @Operation(summary = "Reminder update by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ReminderResponseDto>> updateReminder(
            @PathVariable Long id, @Valid @RequestBody ReminderUpdateRequestDto dto) {

        ReminderResponseDto updated = reminderService.updateReminder(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Reminder updated successfully", updated));

    }

    @Operation(summary = "Delete reminder by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder successfully deleted",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Not reminder found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteReminder(@PathVariable Long id) {

        reminderService.deleteReminder(id);
        return ResponseEntity.ok(ApiResponseDto.success("Reminder deleted successfully"));

    }
}
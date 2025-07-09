package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>("success", null, data);
    }
    public static <T> ApiResponseDto<T> success(String message) {
        return new ApiResponseDto<>("success", message, null);
    }

    public static <T> ApiResponseDto<T> success(String message, T data) {
        return new ApiResponseDto<>("success", message, data);
    }

    public static <T> ApiResponseDto<T> error(String message) {
        return new ApiResponseDto<>("error", message, null);
    }
}
package ru.sbp.gazii.podelki.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sbp.gazii.podelki.entity.ClientCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClientRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Registration date is mandatory")
    @PastOrPresent(message = "Registration date cannot be in the future")
    private LocalDateTime registrationDate;

    @Builder.Default
    private ClientCategory category = ClientCategory.STANDARD;
}

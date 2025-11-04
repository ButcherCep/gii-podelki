package ru.sbp.gazii.podelki.exceptions;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientAlreadyExistsException extends Throwable {
    public ClientAlreadyExistsException(@NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String s) {
    }
}

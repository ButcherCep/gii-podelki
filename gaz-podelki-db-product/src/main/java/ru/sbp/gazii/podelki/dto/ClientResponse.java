package ru.sbp.gazii.podelki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sbp.gazii.podelki.entity.Client;
import ru.sbp.gazii.podelki.entity.ClientCategory;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private ClientCategory category;
}

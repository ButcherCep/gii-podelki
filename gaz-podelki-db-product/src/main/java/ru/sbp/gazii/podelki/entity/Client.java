package ru.sbp.gazii.podelki.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Client {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private ClientCategory category;
}

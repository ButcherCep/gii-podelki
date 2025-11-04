package ru.sbp.gazii.podelki.controllers;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sbp.gazii.podelki.dto.ClientResponse;
import ru.sbp.gazii.podelki.dto.CreateClientRequest;
import ru.sbp.gazii.podelki.exceptions.ClientAlreadyExistsException;
import ru.sbp.gazii.podelki.mappers.ClientMapper;
import ru.sbp.gazii.podelki.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@Validated
@Slf4j
@AllArgsConstructor
public class ClientEndpoint {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(
            @Valid @RequestBody CreateClientRequest request) throws ClientAlreadyExistsException {

        log.info("Creating new client: {}", request.getEmail());

        ClientResponse response = clientService.createClient(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}

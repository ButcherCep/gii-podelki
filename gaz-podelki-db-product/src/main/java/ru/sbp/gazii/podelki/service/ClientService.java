package ru.sbp.gazii.podelki.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sbp.gazii.podelki.dto.ClientResponse;
import ru.sbp.gazii.podelki.dto.CreateClientRequest;
import ru.sbp.gazii.podelki.entity.Client;
import ru.sbp.gazii.podelki.exceptions.ClientAlreadyExistsException;
import ru.sbp.gazii.podelki.mappers.ClientMapper;
import ru.sbp.gazii.podelki.repository.ClientRepository;

@Service
@Slf4j
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientResponse createClient(@Valid CreateClientRequest request) throws ClientAlreadyExistsException {
        // Проверка на уникальность email
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new ClientAlreadyExistsException(
                    "Client with email " + request.getEmail() + " already exists"
            );
        }

        // Маппинг через MapStruct
        Client client = clientMapper.toEntity(request);

        Client savedClient = clientRepository.save(client);
        log.info("Client created successfully with ID: {}", savedClient.getId());

        // Маппинг обратно в response
        return clientMapper.toResponse(savedClient);
    }
}

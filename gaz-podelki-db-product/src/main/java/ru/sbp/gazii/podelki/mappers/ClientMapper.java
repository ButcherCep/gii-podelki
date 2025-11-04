package ru.sbp.gazii.podelki.mappers;

import org.mapstruct.Mapper;
import ru.sbp.gazii.podelki.dto.ClientResponse;
import ru.sbp.gazii.podelki.dto.CreateClientRequest;
import ru.sbp.gazii.podelki.entity.Client;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    public Client toEntity(CreateClientRequest request) {
        if (request == null) {
            return null;
        }

        return Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .registrationDate(request.getRegistrationDate())
                .category(request.getCategory())
                .build();
    }

    public abstract ClientResponse toResponse(Client client);

    public abstract List<ClientResponse> toResponseList(List<Client> clients);

    public void updateClientFromRequest(CreateClientRequest request, Client client) {
        if (request == null) {
            return;
        }

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setRegistrationDate(request.getRegistrationDate());
        client.setCategory(request.getCategory());
    }
}

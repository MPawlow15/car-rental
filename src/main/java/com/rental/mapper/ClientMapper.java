package com.rental.mapper;

import com.rental.domain.Client;
import com.rental.domain.dto.ClientDto;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {

    public Client mapToClient(final ClientDto clientDto) {
        return new Client(
                clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getPhoneNumber()
        );
    }

    public ClientDto mapToClientDto(final Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhoneNumber()
        );
    }
}
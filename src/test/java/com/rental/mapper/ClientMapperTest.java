package com.rental.mapper;

import com.rental.domain.Client;
import com.rental.domain.dto.ClientDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientMapperTest {

    private ClientMapper clientMapper;

    @BeforeEach
    public void setUp() {
        clientMapper = new ClientMapper();
    }

    @Test
    public void shouldMapToClient() {
        // given
        ClientDto clientDto = new ClientDto(1L, "John", "Doe", "555-1234");

        // when
        Client client = clientMapper.mapToClient(clientDto);

        // then
        Assertions.assertEquals(clientDto.getFirstName(), client.getFirstName());
        Assertions.assertEquals(clientDto.getLastName(), client.getLastName());
        Assertions.assertEquals(clientDto.getPhoneNumber(), client.getPhoneNumber());
    }

    @Test
    public void shouldMapToClientDto() {
        // given
        Client client = new Client("John", "Doe", "555-1234");

        // when
        ClientDto clientDto = clientMapper.mapToClientDto(client);

        // then
        Assertions.assertEquals(client.getId(), clientDto.getId());
        Assertions.assertEquals(client.getFirstName(), clientDto.getFirstName());
        Assertions.assertEquals(client.getLastName(), clientDto.getLastName());
        Assertions.assertEquals(client.getPhoneNumber(), clientDto.getPhoneNumber());
    }
}
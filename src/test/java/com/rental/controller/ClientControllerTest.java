package com.rental.controller;

import com.rental.domain.Client;
import com.rental.domain.dto.ClientDto;
import com.rental.exception.ClientNotFoundException;
import com.rental.mapper.ClientMapper;
import com.rental.service.ClientDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientDbService clientDbService;

    @Mock
    private ClientMapper clientMapper;

    private Client client;
    private ClientDto clientDto;

    @BeforeEach
    public void setUp() {
        client = new Client("John", "Smith", "123456789");
        clientDto = new ClientDto(1L, "John", "Smith", "123456789");
    }

    @Test
    void shouldGetAllClients() throws ClientNotFoundException {
        // Given
        Client client1 = new Client("Jack", "Black", "987654321");
        ClientDto clientDto1 = new ClientDto(2L, "Jack", "Black", "987654321");
        when(clientDbService.getAllClients()).thenReturn(Arrays.asList(client, client1));
        when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);
        when(clientMapper.mapToClientDto(client1)).thenReturn(clientDto1);

        // When
        ResponseEntity<List<ClientDto>> response = clientController.getAllClients();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(clientDto, response.getBody().get(0));
        assertEquals(clientDto1, response.getBody().get(1));
    }

    @Test
    void shouldCreateClient() {
        // Given
        when(clientMapper.mapToClient(clientDto)).thenReturn(client);
        when(clientDbService.saveClient(client)).thenReturn(client);
        when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);

        // When
        ResponseEntity<ClientDto> response = clientController.createClient(clientDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clientDto, response.getBody());
    }

    @Test
    void shouldGetClient() throws ClientNotFoundException {
        // Given
        when(clientDbService.getClient(1L)).thenReturn(client);
        when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);

        // When
        ResponseEntity<ClientDto> response = clientController.getClient(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto, response.getBody());
    }

    @Test
    void shouldUpdateClient() throws ClientNotFoundException {
        // Given
        when(clientMapper.mapToClient(clientDto)).thenReturn(client);
        when(clientDbService.updateClient(1L, client)).thenReturn(client);
        when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);

        // when
        clientController.createClient(clientDto);
        ResponseEntity<ClientDto> response = clientController.updateClient(1L, clientDto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto, response.getBody());
        verify(clientDbService, times(1)).updateClient(1L, client);
    }

    @Test
    void shouldDeleteClient() {
        // Given
        when(clientMapper.mapToClient(clientDto)).thenReturn(client);
        when(clientDbService.saveClient(client)).thenReturn(client);
        when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);

        // When
        clientController.createClient(clientDto);
        ResponseEntity<ClientDto> responseEntity = clientController.deleteClient(1L);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(clientDbService, times(1)).deleteClient(1L);
    }
}
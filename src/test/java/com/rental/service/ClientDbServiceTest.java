package com.rental.service;

import com.rental.domain.Client;
import com.rental.exception.ClientNotFoundException;
import com.rental.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientDbServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientDbService clientDbService;

    @Captor
    private ArgumentCaptor<Client> clientCaptor;

    private final Long clientId = 1L;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("John", "Doe", "123456789");
    }

    @Test
    void testAddClient() {
        //Given
        when(clientRepository.save(client)).thenReturn(client);

        //When
        Client result = clientDbService.saveClient(client);

        //Then
        verify(clientRepository, times(1)).save(clientCaptor.capture());
        Assertions.assertEquals(client, clientCaptor.getValue());
        Assertions.assertEquals(client, result);
    }

    @Test
    void testGetAllClients() {
        //Given
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));

        //When
        List<Client> result = clientDbService.getAllClients();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(client, result.get(0));
    }

    @Test
    void testGetExistingClient() throws ClientNotFoundException {
        //Given
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        //When
        Client result = clientDbService.getClient(clientId);

        //Then
        Assertions.assertEquals(client, result);
    }

    @Test
    void testGetNonExistingClient() {
        //Given
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientDbService.getClient(clientId));
    }

}
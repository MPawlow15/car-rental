package com.rental.service;

import com.rental.domain.Client;
import com.rental.exception.ClientNotFoundException;
import com.rental.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientDbService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClient(final Long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    public Client saveClient(final Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(final Long id) {
        clientRepository.deleteById(id);
    }
}

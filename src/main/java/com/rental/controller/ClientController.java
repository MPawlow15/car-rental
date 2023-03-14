package com.rental.controller;

import com.rental.domain.Client;
import com.rental.domain.dto.ClientDto;
import com.rental.exception.ClientNotFoundException;
import com.rental.mapper.ClientMapper;
import com.rental.service.ClientDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientDbService clientDbService;
    private final ClientMapper clientMapper;

    public ClientController(ClientDbService clientDbService, ClientMapper clientMapper) {
        this.clientDbService = clientDbService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        Client client = clientMapper.mapToClient(clientDto);
        clientDbService.saveClient(client);
        return new ResponseEntity<>(clientMapper.mapToClientDto(client), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<Client> clients = clientDbService.getAllClients();
        List<ClientDto> clientDtoList = clients.stream()
                .map(clientMapper::mapToClientDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(clientDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) throws ClientNotFoundException {
        Client client = clientDbService.getClient(id);
        return new ResponseEntity<>(clientMapper.mapToClientDto(client), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        Client client = clientMapper.mapToClient(clientDto);
        clientDbService.saveClient(client);
        return new ResponseEntity<>(clientMapper.mapToClientDto(client), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable Long id) {
        clientDbService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
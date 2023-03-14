package com.rental.controller;

import com.rental.domain.Reckoning;
import com.rental.domain.dto.ReckoningDto;
import com.rental.exception.ReckoningNotFoundException;
import com.rental.mapper.ReckoningMapper;
import com.rental.service.ReckoningDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reckonings")
public class ReckoningController {

    private final ReckoningDbService reckoningDbService;
    private final ReckoningMapper reckoningMapper;

    public ReckoningController(ReckoningDbService reckoningDbService, ReckoningMapper reckoningMapper) {
        this.reckoningDbService = reckoningDbService;
        this.reckoningMapper = reckoningMapper;
    }

    @PostMapping
    public ResponseEntity<ReckoningDto> createReckoning(@RequestBody ReckoningDto reckoningDto) {
        Reckoning reckoning = reckoningMapper.mapToReckoning(reckoningDto);
        reckoningDbService.saveReckoning(reckoning);
        return new ResponseEntity<>(reckoningMapper.mapToReckoningDto(reckoning), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReckoningDto>> getAllReckonings() {
        List<Reckoning> reckonings = reckoningDbService.getAllReckonings();
        List<ReckoningDto> reckoningDtoList = reckonings.stream()
                .map(reckoningMapper::mapToReckoningDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reckoningDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReckoningDto> getReckoning(@PathVariable Long id) throws ReckoningNotFoundException {
        Reckoning reckoning = reckoningDbService.getReckoning(id);
        return new ResponseEntity<>(reckoningMapper.mapToReckoningDto(reckoning), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReckoningDto> updateReckoning(@PathVariable Long id, @RequestBody ReckoningDto reckoningDto) throws ReckoningNotFoundException {
        Reckoning reckoning = reckoningMapper.mapToReckoning(reckoningDto);
        Reckoning updatedReckoning = reckoningDbService.updateReckoning(id, reckoning);
        return new ResponseEntity<>(reckoningMapper.mapToReckoningDto(updatedReckoning), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReckoningDto> deleteReckoning(@PathVariable Long id) {
        reckoningDbService.deleteReckoning(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
package com.rental.controller;

import com.rental.domain.Penalties;
import com.rental.domain.dto.PenaltiesDto;
import com.rental.exception.PenaltiesNotFoundException;
import com.rental.mapper.PenaltiesMapper;
import com.rental.service.PenaltiesDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/penalties")
public class PenaltiesController {

    private final PenaltiesDbService penaltiesDbService;
    private final PenaltiesMapper penaltiesMapper;

    public PenaltiesController(PenaltiesDbService penaltiesDbService, PenaltiesMapper penaltiesMapper) {
        this.penaltiesDbService = penaltiesDbService;
        this.penaltiesMapper = penaltiesMapper;
    }

    @PostMapping
    public ResponseEntity<PenaltiesDto> createPenalties(@RequestBody PenaltiesDto penaltiesDto) {
        Penalties penalties = penaltiesMapper.mapToPenalties(penaltiesDto);
        penaltiesDbService.addPenalties(penalties);
        return new ResponseEntity<>(penaltiesMapper.mapToPenaltiesDto(penalties), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PenaltiesDto>> getAllPenalties() {
        List<Penalties> penalties = penaltiesDbService.getAllPenalties();
        List<PenaltiesDto> penaltiesDtoList = penalties.stream()
                .map(penaltiesMapper::mapToPenaltiesDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(penaltiesDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenaltiesDto> getPenalties(@PathVariable Long id) throws PenaltiesNotFoundException {
        Penalties penalties = penaltiesDbService.getPenaltiesById(id);
        return new ResponseEntity<>(penaltiesMapper.mapToPenaltiesDto(penalties), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PenaltiesDto> updatePenalties(@PathVariable Long id,
                                                        @RequestBody PenaltiesDto penaltiesDto) throws PenaltiesNotFoundException {
        Penalties penalties = penaltiesMapper.mapToPenalties(penaltiesDto);
        Penalties updatedPenalties = penaltiesDbService.updatePenalties(id, penalties);
        return new ResponseEntity<>(penaltiesMapper.mapToPenaltiesDto(updatedPenalties), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PenaltiesDto> deletePenalties(@PathVariable Long id) {
        penaltiesDbService.deletePenalties(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
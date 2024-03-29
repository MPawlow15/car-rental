package com.rental.controller;

import com.rental.domain.Hire;
import com.rental.domain.dto.HireDto;
import com.rental.exception.HireNotFoundException;
import com.rental.mapper.HireMapper;
import com.rental.service.HireDbService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hires")
public class HireController {

    private final HireDbService hireDbService;
    private final HireMapper hireMapper;

    public HireController(HireDbService hireDbService, HireMapper hireMapper) {
        this.hireDbService = hireDbService;
        this.hireMapper = hireMapper;
    }

    @ApiOperation(value = "Create a new hire")
    @PostMapping
    public ResponseEntity<HireDto> createHire(@RequestBody HireDto hireDto) {
        Hire hire = hireMapper.mapToHire(hireDto);
        hireDbService.addHire(hire);
        return new ResponseEntity<>(hireMapper.mapToHireDto(hire), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get all hires")
    @GetMapping
    public ResponseEntity<List<HireDto>> getAllHires() {
        List<Hire> hires = hireDbService.getAllHires();
        List<HireDto> hireDtoList = hires.stream()
                .map(hireMapper::mapToHireDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hireDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a hire by id")
    @GetMapping("/{id}")
    public ResponseEntity<HireDto> getHire(@PathVariable Long id) throws HireNotFoundException {
        Hire hire = hireDbService.getHireById(id);
        return new ResponseEntity<>(hireMapper.mapToHireDto(hire), HttpStatus.OK);
    }

    @ApiOperation(value = "Update a hire")
    @PutMapping("/{id}")
    public ResponseEntity<HireDto> updateHire(@PathVariable Long id, @RequestBody HireDto hireDto) throws HireNotFoundException{
        Hire hire = hireMapper.mapToHire(hireDto);
        hireDbService.updateHire(id, hire);
        return new ResponseEntity<>(hireMapper.mapToHireDto(hire), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a hire")
    @DeleteMapping("/{id}")
    public ResponseEntity<HireDto> deleteHire(@PathVariable Long id) {
        hireDbService.deleteHire(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
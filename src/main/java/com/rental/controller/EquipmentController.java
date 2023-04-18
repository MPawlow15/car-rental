package com.rental.controller;

import com.rental.domain.Equipment;
import com.rental.domain.dto.EquipmentDto;
import com.rental.exception.EquipmentNotFoundException;
import com.rental.mapper.EquipmentMapper;
import com.rental.service.EquipmentDbService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    private final EquipmentDbService equipmentDbService;
    private final EquipmentMapper equipmentMapper;

    public EquipmentController(EquipmentDbService equipmentDbService, EquipmentMapper equipmentMapper) {
        this.equipmentDbService = equipmentDbService;
        this.equipmentMapper = equipmentMapper;
    }

    @ApiOperation(value = "Create a new equipment")
    @PostMapping
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody EquipmentDto equipmentDto) {
        Equipment equipment = equipmentMapper.mapToEquipment(equipmentDto);
        equipmentDbService.saveEquipment(equipment);
        return new ResponseEntity<>(equipmentMapper.mapToEquipmentDto(equipment), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get all equipments")
    @GetMapping
    public ResponseEntity<List<EquipmentDto>> getAllEquipments() {
        List<Equipment> equipments = equipmentDbService.getAllEquipment();
        List<EquipmentDto> equipmentDtoList = equipments.stream()
                .map(equipmentMapper::mapToEquipmentDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(equipmentDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Get an equipment by id")
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDto> getEquipment(@PathVariable Long id) throws EquipmentNotFoundException {
        Equipment equipment = equipmentDbService.getEquipment(id);
        return new ResponseEntity<>(equipmentMapper.mapToEquipmentDto(equipment), HttpStatus.OK);
    }

    @ApiOperation(value = "Update an equipment")
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDto> updateEquipment(@PathVariable Long id,
                                                        @RequestBody EquipmentDto equipmentDto) throws EquipmentNotFoundException{
        Equipment equipment = equipmentMapper.mapToEquipment(equipmentDto);
        equipmentDbService.updateEquipment(id, equipment);
        return new ResponseEntity<>(equipmentMapper.mapToEquipmentDto(equipment), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an equipment")
    @DeleteMapping("/{id}")
    public ResponseEntity<EquipmentDto> deleteEquipment(@PathVariable Long id) {
        equipmentDbService.deleteEquipment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
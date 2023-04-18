package com.rental.controller;

import com.rental.domain.Penalties;
import com.rental.domain.dto.PenaltiesDto;
import com.rental.exception.PenaltiesNotFoundException;
import com.rental.mapper.PenaltiesMapper;
import com.rental.service.PenaltiesDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PenaltiesControllerTest {

    @InjectMocks
    private PenaltiesController penaltiesController;

    @Mock
    private PenaltiesDbService penaltiesDbService;

    @Mock
    private PenaltiesMapper penaltiesMapper;

    Penalties penalties;
    PenaltiesDto penaltiesDto;

    @BeforeEach
    void setUp() {
        penalties = new Penalties(BigDecimal.valueOf(100), BigDecimal.valueOf(0));
        penaltiesDto = new PenaltiesDto(1L ,BigDecimal.valueOf(100), BigDecimal.valueOf(0));
    }

    @Test
    void shouldGetPenalties() throws PenaltiesNotFoundException {
        // Given
        when(penaltiesDbService.getPenaltiesById(1L)).thenReturn(penalties);
        when(penaltiesMapper.mapToPenaltiesDto(penalties)).thenReturn(penaltiesDto);

        // When
        ResponseEntity<PenaltiesDto> responseEntity = penaltiesController.getPenalties(1L);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(penaltiesDto, responseEntity.getBody());
    }

    @Test
    void shouldGetAllPenalties() {
        //Given
        Penalties penalties1 = new Penalties(BigDecimal.valueOf(100), BigDecimal.valueOf(50));
        PenaltiesDto penaltiesDto1 = new PenaltiesDto(1L, BigDecimal.valueOf(100), BigDecimal.valueOf(50));
        when(penaltiesDbService.getAllPenalties()).thenReturn(Arrays.asList(penalties, penalties1));
        when(penaltiesMapper.mapToPenaltiesDto(penalties)).thenReturn(penaltiesDto);
        when(penaltiesMapper.mapToPenaltiesDto(penalties1)).thenReturn(penaltiesDto1);

        //When
        ResponseEntity<List<PenaltiesDto>> responseEntity = penaltiesController.getAllPenalties();

        //Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(penaltiesDto, responseEntity.getBody().get(0));
        assertEquals(penaltiesDto1, responseEntity.getBody().get(1));
    }

    @Test
    void shouldCreatePenalties() {
        //Given
        when(penaltiesMapper.mapToPenalties(penaltiesDto)).thenReturn(penalties);
        when(penaltiesDbService.addPenalties(penalties)).thenReturn(penalties);
        when(penaltiesMapper.mapToPenaltiesDto(penalties)).thenReturn(penaltiesDto);

        //When
        ResponseEntity<PenaltiesDto> responseEntity = penaltiesController.createPenalties(penaltiesDto);

        //Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(penaltiesDto, responseEntity.getBody());
    }

    @Test
    void shouldUpdatePenalties() throws PenaltiesNotFoundException {
        //Given
        PenaltiesDto penaltiesDto1 = new PenaltiesDto(1L, BigDecimal.valueOf(200), BigDecimal.valueOf(100));
        Penalties penalties1 = new Penalties(BigDecimal.valueOf(200), BigDecimal.valueOf(100));

        when(penaltiesMapper.mapToPenalties(penaltiesDto1)).thenReturn(penalties1);
        when(penaltiesDbService.updatePenalties(1L, penalties1)).thenReturn(penalties1);
        when(penaltiesMapper.mapToPenaltiesDto(penalties1)).thenReturn(penaltiesDto1);

        //When
        ResponseEntity<PenaltiesDto> responseEntity = penaltiesController.updatePenalties(1L, penaltiesDto1);

        //Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(penaltiesDto1, responseEntity.getBody());
    }

    @Test
    void shouldDeletePenalties() {
        //Given
        when(penaltiesMapper.mapToPenalties(penaltiesDto)).thenReturn(penalties);
        when(penaltiesDbService.addPenalties(penalties)).thenReturn(penalties);
        when(penaltiesMapper.mapToPenaltiesDto(penalties)).thenReturn(penaltiesDto);

        //When
        penaltiesController.createPenalties(penaltiesDto);
        ResponseEntity<PenaltiesDto> responseEntity = penaltiesController.deletePenalties(1L);

        //Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(penaltiesDbService, times(1)).deletePenalties(1L);
    }
}
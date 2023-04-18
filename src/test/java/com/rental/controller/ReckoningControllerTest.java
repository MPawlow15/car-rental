package com.rental.controller;

import com.rental.domain.Reckoning;
import com.rental.domain.dto.ReckoningDto;
import com.rental.exception.ReckoningNotFoundException;
import com.rental.mapper.ReckoningMapper;
import com.rental.service.ReckoningDbService;
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
class ReckoningControllerTest {

    @InjectMocks
    private ReckoningController reckoningController;

    @Mock
    private ReckoningDbService reckoningDbService;

    @Mock
    private ReckoningMapper reckoningMapper;

    Reckoning reckoning;
    ReckoningDto reckoningDto;

    @BeforeEach
    public void setUp() {
        reckoning = new Reckoning(BigDecimal.valueOf(100), BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                BigDecimal.valueOf(300));
        reckoningDto = new ReckoningDto(1L, BigDecimal.valueOf(100), BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                BigDecimal.valueOf(300));
    }

    @Test
    void  shouldGetAllReckonings() {
        // Given
        Reckoning reckoning2 = new Reckoning(BigDecimal.valueOf(150), BigDecimal.valueOf(150), BigDecimal.valueOf(150),
                BigDecimal.valueOf(450));
        ReckoningDto reckoningDto2 = new ReckoningDto(2L, BigDecimal.valueOf(150), BigDecimal.valueOf(150),
                BigDecimal.valueOf(150), BigDecimal.valueOf(450));
        when(reckoningDbService.getAllReckonings()).thenReturn(Arrays.asList(reckoning, reckoning2));
        when(reckoningMapper.mapToReckoningDto(reckoning)).thenReturn(reckoningDto);
        when(reckoningMapper.mapToReckoningDto(reckoning2)).thenReturn(reckoningDto2);

        // When
        ResponseEntity<List<ReckoningDto>> response = reckoningController.getAllReckonings();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(reckoningDto, response.getBody().get(0));
        assertEquals(reckoningDto2, response.getBody().get(1));
    }

    @Test
    void shouldGetReckoning() throws ReckoningNotFoundException {
        // Given
        when(reckoningDbService.getReckoning(1L)).thenReturn(reckoning);
        when(reckoningMapper.mapToReckoningDto(reckoning)).thenReturn(reckoningDto);

        // When
        ResponseEntity<ReckoningDto> response = reckoningController.getReckoning(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reckoningDto, response.getBody());
    }

    @Test
    void shouldCreateReckoning() {
        // Given
        when(reckoningMapper.mapToReckoning(reckoningDto)).thenReturn(reckoning);
        when(reckoningDbService.saveReckoning(reckoning)).thenReturn(reckoning);
        when(reckoningMapper.mapToReckoningDto(reckoning)).thenReturn(reckoningDto);

        // When
        ResponseEntity<ReckoningDto> response = reckoningController.createReckoning(reckoningDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reckoningDto, response.getBody());
    }

    @Test
    public void shouldUpdateReckoning() throws ReckoningNotFoundException {
        // Given
        when(reckoningMapper.mapToReckoning(reckoningDto)).thenReturn(reckoning);
        when(reckoningDbService.updateReckoning(1L, reckoning)).thenReturn(reckoning);
        when(reckoningMapper.mapToReckoningDto(reckoning)).thenReturn(reckoningDto);

        // When
        ResponseEntity<ReckoningDto> response = reckoningController.updateReckoning(1L, reckoningDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reckoningDto, response.getBody());
        verify(reckoningDbService, times(1)).updateReckoning(1L, reckoning);
    }

    @Test
    void shouldDeleteReckoning() {
        // Given
        when(reckoningMapper.mapToReckoning(reckoningDto)).thenReturn(reckoning);
        when(reckoningDbService.saveReckoning(reckoning)).thenReturn(reckoning);
        when(reckoningMapper.mapToReckoningDto(reckoning)).thenReturn(reckoningDto);

        // When
        reckoningController.createReckoning(reckoningDto);
        ResponseEntity<ReckoningDto> response = reckoningController.deleteReckoning(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reckoningDbService, times(1)).deleteReckoning(1L);
    }
}
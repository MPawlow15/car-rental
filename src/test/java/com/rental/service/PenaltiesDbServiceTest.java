package com.rental.service;

import com.rental.domain.Penalties;
import com.rental.exception.PenaltiesNotFoundException;
import com.rental.repository.PenaltiesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PenaltiesDbServiceTest {

    private PenaltiesDbService penaltiesDbService;

    @Mock
    private PenaltiesRepository penaltiesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        penaltiesDbService = new PenaltiesDbService(penaltiesRepository);
    }

    @Test
    public void shouldReturnAllPenalties() {
        // given
        List<Penalties> penaltiesList = new ArrayList<>();
        penaltiesList.add(new Penalties(new BigDecimal("50"), new BigDecimal("20")));
        penaltiesList.add(new Penalties(new BigDecimal("100"), new BigDecimal("50")));
        when(penaltiesRepository.findAll()).thenReturn(penaltiesList);

        // when
        List<Penalties> result = penaltiesDbService.getAllPenalties();

        // then
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("50"), result.get(0).getPenaltyForDamage());
        assertEquals(new BigDecimal("20"), result.get(0).getPenaltyForUntimelyReturn());
        assertEquals(new BigDecimal("100"), result.get(1).getPenaltyForDamage());
        assertEquals(new BigDecimal("50"), result.get(1).getPenaltyForUntimelyReturn());
    }

    @Test
    public void shouldReturnPenaltiesById() throws PenaltiesNotFoundException {
        // given
        Penalties penalties = new Penalties(new BigDecimal("50"), new BigDecimal("20"));
        when(penaltiesRepository.findById(1L)).thenReturn(Optional.of(penalties));

        // when
        Penalties result = penaltiesDbService.getPenaltiesById(1L);

        // then
        assertEquals(new BigDecimal("50"), result.getPenaltyForDamage());
        assertEquals(new BigDecimal("20"), result.getPenaltyForUntimelyReturn());
    }

    @Test
    public void shouldThrowPenaltiesNotFoundExceptionWhenPenaltiesByIdNotExist() {
        // given
        when(penaltiesRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(PenaltiesNotFoundException.class, () -> {
            penaltiesDbService.getPenaltiesById(1L);
        });
    }

    @Test
    public void shouldAddPenalties() {
        // given
        Penalties penalties = new Penalties(new BigDecimal("50"), new BigDecimal("20"));
        when(penaltiesRepository.save(any(Penalties.class))).thenReturn(penalties);

        // when
        Penalties result = penaltiesDbService.addPenalties(penalties);

        // then
        assertEquals(new BigDecimal("50"), result.getPenaltyForDamage());
        assertEquals(new BigDecimal("20"), result.getPenaltyForUntimelyReturn());
    }

    @Test
    void addPenalties() {
        // given
        Penalties penalties = new Penalties(BigDecimal.valueOf(100), BigDecimal.valueOf(50));
        when(penaltiesRepository.save(penalties)).thenReturn(penalties);

        // when
        Penalties addedPenalties = penaltiesDbService.addPenalties(penalties);

        // then
        assertNotNull(addedPenalties);
        assertEquals(penalties.getPenaltyForDamage(), addedPenalties.getPenaltyForDamage());
        assertEquals(penalties.getPenaltyForUntimelyReturn(), addedPenalties.getPenaltyForUntimelyReturn());
    }

    @Test
    void updatePenaltiesNotFoundException() {
        // given
        Long penaltiesId = 1L;
        when(penaltiesRepository.findById(penaltiesId)).thenReturn(Optional.empty());
        Penalties newPenalties = new Penalties(BigDecimal.valueOf(150), BigDecimal.valueOf(75));

        // when, then
        assertThrows(PenaltiesNotFoundException.class, () -> {
            penaltiesDbService.updatePenalties(penaltiesId, newPenalties);
        });
    }

    @Test
    void deletePenalties() {
        // given
        Long penaltiesId = 1L;

        // when
        penaltiesDbService.deletePenalties(penaltiesId);

        // then
        verify(penaltiesRepository, times(1)).deleteById(penaltiesId);
    }

}
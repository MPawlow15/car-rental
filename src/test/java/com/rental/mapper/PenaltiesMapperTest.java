package com.rental.mapper;

import com.rental.domain.Penalties;
import com.rental.domain.dto.PenaltiesDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PenaltiesMapperTest {

    private PenaltiesMapper penaltiesMapper = new PenaltiesMapper();

    @Test
    public void shouldMapToPenalties() {
        // given
        PenaltiesDto penaltiesDto = new PenaltiesDto(1L,BigDecimal.valueOf(99.99), BigDecimal.valueOf(0));

        // when
        Penalties penalties = penaltiesMapper.mapToPenalties(penaltiesDto);

        // then
        assertEquals(penaltiesDto.getPenaltyForDamage(), penalties.getPenaltyForDamage());
        assertEquals(penaltiesDto.getPenaltyForUntimelyReturn(), penalties.getPenaltyForUntimelyReturn());
    }

    @Test
    public void shouldMapToPenaltiesDto() {
        // given
        Penalties penalties = new Penalties(BigDecimal.valueOf(99.99), BigDecimal.valueOf(0));

        // when
        PenaltiesDto penaltiesDto = penaltiesMapper.mapToPenaltiesDto(penalties);

        // then
        assertEquals(penalties.getId(), penaltiesDto.getId());
        assertEquals(penalties.getPenaltyForDamage(), penaltiesDto.getPenaltyForDamage());
        assertEquals(penalties.getPenaltyForUntimelyReturn(), penaltiesDto.getPenaltyForUntimelyReturn());
    }

}
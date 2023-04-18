package com.rental.mapper;

import com.rental.domain.Reckoning;
import com.rental.domain.dto.ReckoningDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReckoningMapperTest {
    private final ReckoningMapper mapper = new ReckoningMapper();

    @Test
    public void shouldMapToReckoning() {
        //given
        ReckoningDto reckoningDto = new ReckoningDto(1L, BigDecimal.valueOf(12.5), BigDecimal.valueOf(200.0),
                BigDecimal.valueOf(50.0), BigDecimal.valueOf(262.5));

        //when
        Reckoning reckoning = mapper.mapToReckoning(reckoningDto);

        //then
        assertNotNull(reckoning);
        assertEquals(reckoningDto.getFuelUsed(), reckoning.getFuelUsed());
        assertEquals(reckoningDto.getEquipmentPrice(), reckoning.getEquipmentPrice());
        assertEquals(reckoningDto.getPenaltiesPrice(), reckoning.getPenaltiesPrice());
        assertEquals(reckoningDto.getTotalPrice(), reckoning.getTotalPrice());
    }

    @Test
    public void shouldMapToReckoningDto() {
        //given
        Reckoning reckoning = new Reckoning(BigDecimal.valueOf(12.5), BigDecimal.valueOf(200.0),
                BigDecimal.valueOf(50.0), BigDecimal.valueOf(262.5));

        //when
        ReckoningDto reckoningDto = mapper.mapToReckoningDto(reckoning);

        //then
        assertNotNull(reckoningDto);
        assertEquals(reckoning.getFuelUsed(), reckoningDto.getFuelUsed());
        assertEquals(reckoning.getEquipmentPrice(), reckoningDto.getEquipmentPrice());
        assertEquals(reckoning.getPenaltiesPrice(), reckoningDto.getPenaltiesPrice());
        assertEquals(reckoning.getTotalPrice(), reckoningDto.getTotalPrice());
    }
}
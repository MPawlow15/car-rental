package com.rental.mapper;

import com.rental.domain.Reckoning;
import com.rental.domain.dto.ReckoningDto;
import org.springframework.stereotype.Service;

@Service
public class ReckoningMapper {

    public Reckoning mapToReckoning(final ReckoningDto reckoningDto) {
        return new Reckoning(
                reckoningDto.getFuelUsed(),
                reckoningDto.getEquipmentPrice(),
                reckoningDto.getPenaltiesPrice(),
                reckoningDto.getTotalPrice()
        );
    }

    public ReckoningDto mapToReckoningDto(final Reckoning reckoning) {
        return new ReckoningDto(
                reckoning.getId(),
                reckoning.getFuelUsed(),
                reckoning.getEquipmentPrice(),
                reckoning.getPenaltiesPrice(),
                reckoning.getTotalPrice()
        );
    }
}

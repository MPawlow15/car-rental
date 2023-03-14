package com.rental.mapper;

import com.rental.domain.Penalties;
import com.rental.domain.dto.PenaltiesDto;
import org.springframework.stereotype.Service;

@Service
public class PenaltiesMapper {

    public Penalties mapToPenalties(final PenaltiesDto penaltiesDto) {
        return new Penalties(
                penaltiesDto.getPenaltyForDamage(),
                penaltiesDto.getPenaltyForUntimelyReturn()
        );
    }

    public PenaltiesDto mapToPenaltiesDto(final Penalties penalties) {
        return new PenaltiesDto(
                penalties.getId(),
                penalties.getPenaltyForDamage(),
                penalties.getPenaltyForUntimelyReturn()
        );
    }
}
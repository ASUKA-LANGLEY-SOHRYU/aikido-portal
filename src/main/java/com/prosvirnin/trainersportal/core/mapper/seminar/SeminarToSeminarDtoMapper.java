package com.prosvirnin.trainersportal.core.mapper.seminar;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.seminar.Seminar;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarDto;
import org.springframework.stereotype.Component;

@Component
public class SeminarToSeminarDtoMapper implements Mapper<Seminar, SeminarDto> {
    @Override
    public SeminarDto map(Seminar source) {
        return SeminarDto.builder()
                .id(source.getId())
                .name(source.getName())
                .start(source.getStart())
                .location(source.getLocation())
                .price(source.getPrice())
                .annualFeePrice(source.getAnnualFeePrice())
                .passportFeePrice(source.getPassportFeePrice())
                .attestationFee5To2Kyu(source.getAttestationFee5To2Kyu())
                .attestationFee1Kyu(source.getAttestationFee1Kyu())
                .attestationFeeBlackBelt(source.getAttestationFeeBlackBelt())
                .build();
    }
}

package com.mohsintech.supplement_app.service.serviceImpl;

import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.dto.SupplementResponse;
import com.mohsintech.supplement_app.model.Supplement;
import org.springframework.data.domain.Page;

import java.util.List;


public class SupplementServiceHelpers {

    //map Supplement class to a DTO(Data Transfer Object) which will be returned to a user.
    public static SupplementDto mapToDto(Supplement supplement){
        return SupplementDto.builder()
                .name(supplement.getName())
                .benefits(supplement.getBenefits())
                .description(supplement.getDescription())
                .evidence(supplement.getEvidence())
                .build();
    }

    public static Supplement mapToEntity(SupplementDto supplementDto){
        return Supplement.builder()
                .name(supplementDto.getName())
                .benefits(supplementDto.getBenefits())
                .description(supplementDto.getDescription())
                .evidence(supplementDto.getEvidence())
                .build();
    }

    public static SupplementResponse mapToResponse(Page<Supplement> page) {
        //extract content from page retrieved
        List<SupplementDto> content = page
                .getContent()
                .stream()
                .map(SupplementServiceHelpers::mapToDto)
                .toList();
        //create the response for the get all supplements request
        return SupplementResponse.builder()
                .content(content)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .last(page.isLast())
                .build();
    }
}

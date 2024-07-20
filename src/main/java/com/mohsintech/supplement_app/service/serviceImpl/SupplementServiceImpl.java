package com.mohsintech.supplement_app.service.serviceImpl;


import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.model.Supplement;
import com.mohsintech.supplement_app.repository.SupplementRepository;
import com.mohsintech.supplement_app.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplementServiceImpl implements SupplementService {
    //repository for db access to supplements
    private final SupplementRepository supplementRepository;

    //initialize beans required for this service
    @Autowired
    public SupplementServiceImpl(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

    //return one supplement to controller
    @Override
    public SupplementDto getSupplement(int supplementId) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(() ->
                new RuntimeException("Supplement Not Found"));
        return mapToDto(supplement);
    }

    //map Supplement class to a DTO(Data Transfer Object) which will be returned to a user.
    private SupplementDto mapToDto(Supplement supplement){
        return SupplementDto.builder()
                .name(supplement.getName())
                .benefits(supplement.getBenefits())
                .description(supplement.getDescription())
                .evidence(supplement.getEvidence())
                .build();
    }



}

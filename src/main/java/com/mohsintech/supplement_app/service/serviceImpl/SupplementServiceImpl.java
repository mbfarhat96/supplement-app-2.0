package com.mohsintech.supplement_app.service.serviceImpl;


import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.dto.SupplementResponse;
import com.mohsintech.supplement_app.exception.SupplementNotFoundException;
import com.mohsintech.supplement_app.model.Supplement;
import com.mohsintech.supplement_app.repository.SupplementRepository;
import com.mohsintech.supplement_app.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplementServiceImpl implements SupplementService {
    //repository for db access to supplements
    private final SupplementRepository supplementRepository;

    //initialize beans required for this service
    @Autowired
    public SupplementServiceImpl(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

    //create a supplement
    @Override
    public SupplementDto createSupplement(SupplementDto supplementDto) {
        Supplement newSupplement = mapToEntity(supplementDto);
        supplementRepository.save(newSupplement);
        return mapToDto(newSupplement);
    }

    //return one supplement to controller
    @Override
    public SupplementDto getSupplement(int supplementId) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(() ->
                new SupplementNotFoundException("Supplement To Retrieve Not Found"));
        return mapToDto(supplement);
    }

    @Override
    public SupplementResponse getSupplements(int pageNo,int pageSize) {
        Pageable pageInfo = PageRequest.of(pageNo,pageSize);
        Page<Supplement> page = supplementRepository.findAll(pageInfo);
        return mapToResponse(page);
    }

    //update a supplement
    @Override
    public SupplementDto updateSupplement(int supplementId, SupplementDto supplementDto) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(() ->
                new SupplementNotFoundException("Supplement To Update Not Found"));
        //map the supplement retrieved from the repository to DTO received by user.
        Supplement updatedSupplement = Supplement.builder()
                .id(supplement.getId())
                .name(supplementDto.getName())
                .description(supplementDto.getDescription())
                .benefits(supplementDto.getBenefits())
                .evidence(supplementDto.getEvidence())
                .build();

        supplementRepository.save(updatedSupplement);

        return mapToDto(updatedSupplement);
    }

    //delete a supplement
    @Override
    public void deleteSupplement(int supplementId) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(() ->
                new SupplementNotFoundException("Supplement To Delete Not Found"));
        supplementRepository.delete(supplement);
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

    private Supplement mapToEntity(SupplementDto supplementDto){
        return Supplement.builder()
                .name(supplementDto.getName())
                .benefits(supplementDto.getBenefits())
                .description(supplementDto.getDescription())
                .evidence(supplementDto.getEvidence())
                .build();
    }

    private SupplementResponse mapToResponse(Page<Supplement> page) {
        //extract content from page retrieved
        List<SupplementDto> content = page
                .getContent()
                .stream()
                .map(this::mapToDto)
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

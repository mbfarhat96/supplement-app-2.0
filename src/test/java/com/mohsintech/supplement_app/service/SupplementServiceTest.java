package com.mohsintech.supplement_app.service;

import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.dto.SupplementResponse;
import com.mohsintech.supplement_app.model.Supplement;
import com.mohsintech.supplement_app.repository.SupplementRepository;
import com.mohsintech.supplement_app.service.serviceImpl.SupplementServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplementServiceTest {

    @Mock
    private SupplementRepository supplementRepository;

    @InjectMocks
    private SupplementServiceImpl supplementService;


    private Supplement supp;
    private SupplementDto suppDto;

    @BeforeEach
    void setUp() {
         supp = Supplement.builder()
                .name("Magnesium")
                .description("A element that is essential for physical function")
                .evidence("Strong")
                .benefits("Muscle Relaxation, Focus, Muscle Performance").build();
        suppDto = SupplementDto.builder()
                .name("Magnesium")
                .description("A element that is essential for physical function")
                .evidence("Strong")
                .benefits("Muscle Relaxation, Focus, Muscle Performance").build();
    }


    @Test
    public void SupplementService_CreateSupplement_ReturnSupplementDto(){
        when(supplementRepository.save(Mockito.any(Supplement.class))).thenReturn(supp);

        SupplementDto returnedSupplementDto = supplementService.createSupplement(suppDto);

        Assertions.assertThat(returnedSupplementDto).isNotNull();
        Assertions.assertThat(returnedSupplementDto).isEqualTo(suppDto);
    }

    @Test
    public void SupplementService_GetSupplementById_ReturnSupplementDto(){
        when(supplementRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(supp));

        SupplementDto returnedSupplementDto = supplementService.getSupplement(0);

        Assertions.assertThat(returnedSupplementDto).isNotNull();
        Assertions.assertThat(returnedSupplementDto).isEqualTo(suppDto);
    }

    @Test
    public void SupplementService_GetSupplements_ReturnSupplementResponse(){
        Page<Supplement> supplementPage = new PageImpl<>(List.of(new Supplement(), new Supplement()), PageRequest.of(1, 10), 2);
        when(supplementRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(supplementPage);

        SupplementResponse returnedSupplementDto = supplementService.getSupplements(1,10);

        Assertions.assertThat(returnedSupplementDto).isNotNull();
        Assertions.assertThat(returnedSupplementDto.getPageSize()).isEqualTo(10);
    }

    @Test
    public void SupplementService_UpdateSupplement_ReturnUpdatedSupplementDto(){
        when(supplementRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(supp));
        when(supplementRepository.save(Mockito.any(Supplement.class))).thenReturn(supp);
        suppDto = SupplementDto.builder()
                .name("Vitamin D3")
                .description("Sunlight Absorber and Calcium Enhancer")
                .evidence("Strong")
                .benefits("Muscle Relaxation, Focus, Muscle Performance").build();

        SupplementDto returnedSupplementDto = supplementService.updateSupplement(0,suppDto);

        Assertions.assertThat(returnedSupplementDto).isNotNull();
        Assertions.assertThat(returnedSupplementDto.getName()).isEqualTo("Vitamin D3");
    }

    @Test
    public void SupplementService_DeleteSupplement_ReturnEmpty(){
        when(supplementRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(supp));

        assertAll(() -> supplementService.deleteSupplement(1));
    }





}

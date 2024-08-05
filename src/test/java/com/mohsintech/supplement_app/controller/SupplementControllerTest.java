package com.mohsintech.supplement_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.dto.SupplementResponse;
import com.mohsintech.supplement_app.model.Supplement;
import com.mohsintech.supplement_app.security.JwtService;
import com.mohsintech.supplement_app.security.JwtFilter;
import com.mohsintech.supplement_app.service.SupplementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = SupplementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SupplementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private SupplementService supplementService;

    @Autowired
    private ObjectMapper objectMapper;

    private Supplement supp;
    private SupplementDto suppDto;
    private SupplementResponse suppResponse;
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

        suppResponse = SupplementResponse.builder()
                .content(Collections.singletonList(suppDto))
                .pageNo(1)
                .pageSize(10)
                .totalElements(1L)
                .totalPages(1)
                .last(false)
                .build();
    }

    @Test
    public void SupplementController_createSupplement_ReturnOK() throws Exception {
        given(supplementService.createSupplement(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/private/supplement/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(suppDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Magnesium"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.evidence").value("Strong"));
    }

    @Test
    public void SupplementController_GetSupplement_ReturnOK() throws Exception {
        given(supplementService.getSupplement(1)).willReturn(suppDto);
        ResultActions response = mockMvc.perform(get("/api/public/supplement/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Magnesium"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.evidence").value("Strong"));
    }

    @Test
    public void SupplementController_GetSupplements_ReturnOk() throws Exception {
        given(supplementService.getSupplements(1,10)).willReturn(suppResponse);

        ResultActions response = mockMvc.perform(get("/api/public/supplement?pageNo=1&pageSize=10")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value("false"));
    }

    @Test
    public void SupplementController_UpdateSupplement_ReturnOK() throws Exception {
        when(supplementService.updateSupplement(1,suppDto)).thenReturn(suppDto);

        ResultActions response = mockMvc.perform(put("/api/private/supplement/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(suppDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Magnesium"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.evidence").value("Strong"));
    }

    @Test
    public void SupplementController_DeleteSupplement_ReturnOK() throws Exception {
        doNothing().when(supplementService).deleteSupplement(1);

        ResultActions response = mockMvc.perform(delete("/api/private/supplement/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Deleted Successfully"));
    }
}

package com.mohsintech.supplement_app.controller;

import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class SupplementController {

    private final SupplementService supplementService;
    //initialize beans required for this controller
    @Autowired
    public SupplementController(SupplementService supplementService) {
        this.supplementService = supplementService;
    }

    //return one supplement
    @GetMapping("supplement/{id}")
    public ResponseEntity<SupplementDto> getSupplement(@PathVariable(value = "id")int supplementId){
        SupplementDto response = supplementService.getSupplement(supplementId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

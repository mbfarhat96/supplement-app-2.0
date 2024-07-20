package com.mohsintech.supplement_app.controller;

import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.dto.SupplementResponse;
import com.mohsintech.supplement_app.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("supplement")
    public ResponseEntity<SupplementResponse> getSupplements(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false)int pageSize){
        SupplementResponse response = supplementService.getSupplements(pageNo,pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("supplement/create")
    public ResponseEntity<SupplementDto> createPokemon(@RequestBody SupplementDto supplementDto){
        SupplementDto response = supplementService.createSupplement(supplementDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("supplement/{id}/update")
    public ResponseEntity<SupplementDto> updateSupplement(@PathVariable(value = "id")int supplementId, @RequestBody SupplementDto supplementDto){
        SupplementDto response = supplementService.updateSupplement(supplementId,supplementDto);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("supplement/{id}/delete")
        public ResponseEntity<String> deleteSupplement(@PathVariable(value = "id")int supplementId){
            supplementService.deleteSupplement(supplementId);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }

}

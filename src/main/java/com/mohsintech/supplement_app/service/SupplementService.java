package com.mohsintech.supplement_app.service;

import com.mohsintech.supplement_app.dto.SupplementDto;
import com.mohsintech.supplement_app.dto.SupplementResponse;

public interface SupplementService {

    SupplementDto createSupplement(SupplementDto supplementDto);

    SupplementDto getSupplement(int supplementId);

    SupplementResponse getSupplements(int pageNo, int pageSize);

    SupplementDto updateSupplement(int supplementId, SupplementDto supplementDto);

    void deleteSupplement(int supplementId);


}

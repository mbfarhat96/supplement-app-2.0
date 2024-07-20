package com.mohsintech.supplement_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplementDto {

    private String name;
    private String description;
    private String benefits;
    private String evidence;
}


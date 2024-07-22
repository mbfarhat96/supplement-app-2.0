package com.mohsintech.supplement_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplementDto {
    @NotBlank(message = "The name of the supplement is required!")
    @Size(min = 4, max = 50, message = "The name of the supplement has to be between 4 and 50 characters!")
    private String name;

    @NotBlank(message = "The description of the supplement is required!")
    @Size(max = 500, message = "The supplement description can't exceed 500 characters!")
    private String description;

    @NotBlank(message = "The benefits of the supplement is required!")
    @Size(max = 500, message = "The benefits of the supplement has to be less then 500 characters")
    private String benefits;

    @NotBlank(message = "The evidence of the supplement is required!")
    @Size(max = 20, message = "The evidence of the supplement has to be less then 20 characters")
    private String evidence;
}


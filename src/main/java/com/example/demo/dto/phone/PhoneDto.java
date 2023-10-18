package com.example.demo.dto.phone;

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
public class PhoneDto {

    @NotBlank(message = "Phone number cannot be null")
    @Size(min = 13, max = 13)
    private String number;
}

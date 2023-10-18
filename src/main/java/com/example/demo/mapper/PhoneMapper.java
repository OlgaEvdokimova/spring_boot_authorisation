package com.example.demo.mapper;

import com.example.demo.dto.phone.PhoneDto;
import com.example.demo.entity.Phone;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhoneMapper {

    public List<PhoneDto> toPhoneDtos(List<Phone> phones) {
        return phones.stream()
                .map(phone -> PhoneDto.builder()
                        .number(phone.getNumber())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Phone> toPhones(List<PhoneDto> phoneDtos) {
        return phoneDtos.stream()
                .map(dto -> Phone.builder()
                        .number(dto.getNumber())
                        .build())
                .collect(Collectors.toList());
    }
}

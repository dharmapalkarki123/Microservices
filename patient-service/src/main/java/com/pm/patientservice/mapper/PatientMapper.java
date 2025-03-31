package com.pm.patientservice.mapper;

import com.pm.patientservice.DTO.PatientResponseDto;
import com.pm.patientservice.model.Patient;

public class PatientMapper {

    public static PatientResponseDto toDTO(Patient patient) {
        PatientResponseDto patientDto = new PatientResponseDto();
        patientDto.setId(patient.getId().toString());
        patientDto.setName(patient.getName());
        patientDto.setAddress(patient.getAddress());
        patientDto.setEmail(patient.getEmail());
        patientDto.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientDto;
    }

}

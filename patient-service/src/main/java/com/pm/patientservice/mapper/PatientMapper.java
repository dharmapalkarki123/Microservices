package com.pm.patientservice.mapper;

import com.pm.patientservice.DTO.PatientRequestDTO;
import com.pm.patientservice.DTO.PatientResponseDto;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

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

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {

        Patient patient=new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;

    }

}

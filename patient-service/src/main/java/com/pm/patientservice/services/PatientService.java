package com.pm.patientservice.services;

import com.pm.patientservice.DTO.PatientResponseDto;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repositories.PatientRepository;

import java.util.List;

public class PatientService {
    private final PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatient(){

        List<Patient> patients= patientRepository.findAll();

        List<PatientResponseDto> patientResponseDto=patients.stream()
                .map(PatientMapper::toDTO).toList();

        return patientResponseDto;

    }

}

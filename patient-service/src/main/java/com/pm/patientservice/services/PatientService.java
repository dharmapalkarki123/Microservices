package com.pm.patientservice.services;

import com.pm.patientservice.DTO.PatientRequestDTO;
import com.pm.patientservice.DTO.PatientResponseDto;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients(){

        List<Patient> patients= patientRepository.findAll();

        List<PatientResponseDto> patientResponseDto=patients.stream()
                .map(PatientMapper::toDTO).toList();

        return patientResponseDto;

    }

    public PatientResponseDto createPatient(PatientRequestDTO patientRequestDTO){
        Patient newPatient=patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }

}

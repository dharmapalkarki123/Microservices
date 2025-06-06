package com.pm.patientservice.services;

import com.pm.patientservice.DTO.PatientRequestDTO;
import com.pm.patientservice.DTO.PatientResponseDto;
import com.pm.patientservice.exception.EmailAlreadyExistException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repositories.PatientRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {

        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDto> getPatients(){

        List<Patient> patients= patientRepository.findAll();

        List<PatientResponseDto> patientResponseDto=patients.stream()
                .map(PatientMapper::toDTO).toList();

        return patientResponseDto;

    }

    public PatientResponseDto createPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistException("Patient with this email is already exist" + patientRequestDTO.getEmail());
        }


        Patient newPatient=patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getName(), newPatient.getEmail());

              kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDTO patientRequestDTO){

        Patient patient=patientRepository.findById(id)
                .orElseThrow(
                ()->new PatientNotFoundException("Patient not found with ID:" + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistException("Patient with this email is already exist" + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient=patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);



    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

}

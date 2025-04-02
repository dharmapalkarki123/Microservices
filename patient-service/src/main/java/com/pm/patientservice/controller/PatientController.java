package com.pm.patientservice.controller;

import com.pm.patientservice.DTO.PatientRequestDTO;
import com.pm.patientservice.DTO.PatientResponseDto;

import com.pm.patientservice.DTO.validation.CreatePatientValidationGroup;
import com.pm.patientservice.exception.EmailAlreadyExistException;
import com.pm.patientservice.repositories.PatientRepository;
import com.pm.patientservice.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private  PatientService patientService;

    public PatientController(PatientService patientService, PatientRepository patientRepository) {
        this.patientService = patientService;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    @Operation(summary = "Get Patient")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        List<PatientResponseDto> patients=patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO){



        PatientResponseDto patientResponseDto=patientService.createPatient(patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDto);
    }
@Operation(summary = "Update Existing Patient")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id){
        PatientResponseDto patientResponseDto= patientService.updatePatient(id,patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDto);


    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }



}

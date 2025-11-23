package org.example.controller;


import jakarta.validation.Valid;
import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantService mutantService;

    public MutantController(MutantService mutantService){
        this.mutantService=mutantService;
    }

    @PostMapping
    public ResponseEntity<Void>checkMutant(@Valid @RequestBody DnaRequest dnaRequest){
        boolean isMutant = mutantService.analyzeDna(dnaRequest.getDna());

        if(isMutant){
            return ResponseEntity.ok().build(); //200 OK
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();//403 Forbidden
        }
    }
}

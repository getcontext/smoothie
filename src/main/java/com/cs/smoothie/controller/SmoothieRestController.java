package com.cs.smoothie.controller;

import com.cs.smoothie.model.Smoothie;
import com.cs.smoothie.repository.SmoothieRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Rest Controller for smoothies
 */
@RestController
@RequestMapping(value = "/api/smoothie", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class SmoothieRestController {
    private final SmoothieRepository smoothieRepository;

    @Autowired
    public SmoothieRestController(SmoothieRepository repository) {
        this.smoothieRepository = repository;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Smoothie>> getAllSmoothies(@RequestParam(required = false) String name) {
        try {
            List<Smoothie> smoothies = new ArrayList<>();
            if (name == null)
                smoothies.addAll(smoothieRepository.findAll());
            else
                smoothieRepository.findByNameContaining(name).forEach(smoothies::add);

            if (smoothies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(smoothies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Smoothie> getSmoothieById(@PathVariable("id") @Positive(message = "id must be a positive number") long id) {
        Optional<Smoothie> tutorialData = smoothieRepository.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping(params = {"isEnabled"})
//    public List<Smoothie> getAllFilteredByEnabled(
//            @RequestParam boolean isEnabled
//    ) {
//        return service.getSmoothiesEnabled();
//    }

    @PostMapping("/save")
    public ResponseEntity<Smoothie> createSmoothie(@Valid @RequestBody Smoothie smoothie) {
        try {
            smoothieRepository.save(smoothie);
            return new ResponseEntity<>(smoothie, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Smoothie> updateSmoothie(@PathVariable("id") @Positive(message = "id must be a positive number") long id, @RequestBody Smoothie smoothie) {
        Optional<Smoothie> smoothieOptional = smoothieRepository.findById(id);

        if (smoothieOptional.isPresent()) {
            Smoothie smoothieLocal = smoothieOptional.get();
            smoothieLocal.setName(smoothie.getName());
            smoothieLocal.setDetails(smoothie.getDetails());
            smoothieLocal.setNutrition(smoothie.getNutrition());
            smoothieLocal.setEnabled(smoothie.isEnabled());
            return new ResponseEntity<>(smoothieRepository.save(smoothieLocal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Smoothie> delete(@PathVariable("id") @Positive(message = "id must be a positive number") Long id) {
        try {
            smoothieRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

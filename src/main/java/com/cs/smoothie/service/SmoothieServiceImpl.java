package com.cs.smoothie.service;

import com.cs.smoothie.model.Smoothie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SmoothieServiceImpl implements SmoothieService {
    private List<Smoothie> smoothies = new ArrayList<>(
            Arrays.asList(
                    new Smoothie(),
                    new Smoothie(),
                    new Smoothie(),
                    new Smoothie(),
                    new Smoothie()
            )
    );

    @Override
    public List<Smoothie> getAllSmoothies() {
        return smoothies;
    }

    @Override
    public Smoothie getById(Long id) {
        return smoothies.stream()
                .filter(smoothie -> smoothie.getId() == id)
                .findAny()
                .orElseThrow();
    }

    @Override
    public Smoothie update(Long id, Smoothie Smoothie) {
        Smoothie SmoothieUpdated = getById(id);
        SmoothieUpdated.setName(Smoothie.getName());
        return SmoothieUpdated;
    }

    @Override
    public Smoothie create(Smoothie smoothie) {
        Long newId = smoothies.stream().mapToLong(smoothie1 -> Long.valueOf(smoothie1.getId())).max().orElse(0L) + 1L;
        smoothie.setId(newId);
        smoothies.add(smoothie);
        return getById(smoothie.getId());
    }

    @Override
    public void delete(Long id) {
        boolean isDeleted = smoothies.removeIf(Smoothie -> Smoothie.getId() == id);
        if (!isDeleted) {
            throw new NoSuchElementException();
        }
    }
}

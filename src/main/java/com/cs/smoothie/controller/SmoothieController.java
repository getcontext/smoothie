package com.cs.smoothie.controller;

import com.cs.smoothie.client.RestClientSmoothie;
import com.cs.smoothie.model.Smoothie;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("smoothie")
public class SmoothieController {
    protected static final String ROUTE_SMOOTHIE_ALL = "/smoothie/all";
    protected static final String TPL_SMOOTHIE_FORM = "smoothie_form";
    protected static final String TPL_SMOOTHIE = "smoothie";
    @Autowired
    private RestClientSmoothie restClientSmoothie;

    @PostConstruct
    public void initialize() {
    }

    @GetMapping("/test")
    public String smoothie(@RequestParam(name = "name", required = false, defaultValue = "green") String name, Model model) {
        model.addAttribute("name", name);
        return TPL_SMOOTHIE;
    }

    @GetMapping("/all")
    public String getAll(Model model, @Param("keyword") String keyword) {
        List<Smoothie> smoothies = restClientSmoothie.getAll("get");
        model.addAttribute("smoothies", smoothies);
        return TPL_SMOOTHIE;
    }

    @GetMapping("/new")
    public String addSmoothie(Model model) {
        Smoothie smoothie = new Smoothie();
        smoothie.setEnabled(true);

        model.addAttribute("smoothie", smoothie);
        model.addAttribute("pageTitle", "Create new Smoothie");

        return TPL_SMOOTHIE_FORM;
    }

    @PostMapping("/save")
    public String saveSmoothie(Smoothie smoothie, RedirectAttributes redirectAttributes) {
        restClientSmoothie.post("save", smoothie);
        redirectAttributes.addFlashAttribute("message", "Smoothie saved");
        return "redirect:" + ROUTE_SMOOTHIE_ALL;
    }

    @GetMapping("/{id}")
    public String editSmoothie(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Smoothie smoothie = restClientSmoothie.get("get", id);
        model.addAttribute("smoothie", smoothie);
        model.addAttribute("pageTitle", "Edit Smoothie (ID: " + id + ")");
        return TPL_SMOOTHIE_FORM;
    }

    @GetMapping("/delete/{id}")
    public String deleteSmoothie(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        restClientSmoothie.delete("delete", id);
        redirectAttributes.addFlashAttribute("message", "Smoothie id=" + id + " deleted!");
        return "redirect:" + ROUTE_SMOOTHIE_ALL;
    }

    @GetMapping("/{id}/enabled/{status}")
    public String updateSmoothieEnabled(@PathVariable("id") Long id, @PathVariable("status") boolean enabled,
                                        Model model, RedirectAttributes redirectAttributes) {
        Smoothie smoothie = new Smoothie();
        smoothie.setId(id);
        smoothie.setEnabled(enabled);

        restClientSmoothie.put("update", smoothie);

        String status = enabled ? "enabled" : "disabled";
        String message = "The Smoothie id=" + id + " has been " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:" + ROUTE_SMOOTHIE_ALL;
    }
}

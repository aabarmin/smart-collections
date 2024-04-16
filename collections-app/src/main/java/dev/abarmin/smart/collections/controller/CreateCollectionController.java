package dev.abarmin.smart.collections.controller;

import dev.abarmin.smart.collections.controller.converter.CollectionFormConverter;
import dev.abarmin.smart.collections.controller.model.CollectionForm;
import dev.abarmin.smart.collections.repository.CollectionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CreateCollectionController {
    private final CollectionFormConverter converter;
    private final CollectionRepository collectionRepository;

    @ModelAttribute("form")
    public CollectionForm collectionForm() {
        return new CollectionForm();
    }

    @GetMapping("/collections/new")
    public String newCollection() {
        return "collection/form";
    }

    @PostMapping("/collections")
    public String saveCollection(@Valid @ModelAttribute("form") CollectionForm form,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "collection/form";
        }
        collectionRepository.save(converter.convert(form));
        return "redirect:/my";
    }
}

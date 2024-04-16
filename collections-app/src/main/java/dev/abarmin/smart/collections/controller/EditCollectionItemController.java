package dev.abarmin.smart.collections.controller;

import dev.abarmin.smart.collections.controller.converter.CollectionItemFormConverter;
import dev.abarmin.smart.collections.controller.model.CollectionItemForm;
import dev.abarmin.smart.collections.repository.CollectionItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EditCollectionItemController {
    private final CollectionItemRepository repository;
    private final CollectionItemFormConverter converter;

    @GetMapping("/collections/{id}/albums/add")
    public String createAlbum(@PathVariable("id") int collectionId,
                              Model model) {

        model.addAttribute("form", CollectionItemForm.builder()
                .collectionId(collectionId)
                .build());
        return "collection-item/form";
    }

    @PostMapping("/collections/{id}/albums")
    public String saveAlbum(@Valid @ModelAttribute("form") CollectionItemForm form,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "collection-item/form";
        }

        repository.save(converter.convert(form));
        return "redirect:/my";
    }
}

package dev.abarmin.smart.collections.controller;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.service.SessionService;
import dev.abarmin.smart.collections.controller.converter.CollectionFormConverter;
import dev.abarmin.smart.collections.controller.model.CollectionForm;
import dev.abarmin.smart.collections.entity.CollectionEntity;
import dev.abarmin.smart.collections.exception.NoAccessException;
import dev.abarmin.smart.collections.exception.NoCurrentUserException;
import dev.abarmin.smart.collections.repository.CollectionRepository;
import dev.abarmin.smart.collections.service.AccessService;
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
public class EditCollectionController {
    private final AccessService accessService;
    private final SessionService sessionService;
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

    @GetMapping("/collections/{id}/edit")
    public String editCollection(@PathVariable("id") int collectionId,
                                 Model model) {
        final UserInfo userInfo = sessionService.getCurrentUser().orElseThrow(NoCurrentUserException::new);
        final CollectionEntity collection = collectionRepository.findById(collectionId).orElseThrow();
        if (!accessService.hasAccess(userInfo, collection)) {
            throw new NoAccessException();
        }
        model.addAttribute("form", converter.convert(collection));
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

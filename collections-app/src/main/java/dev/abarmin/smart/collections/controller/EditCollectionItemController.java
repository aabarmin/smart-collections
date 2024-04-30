package dev.abarmin.smart.collections.controller;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.service.SessionService;
import dev.abarmin.smart.collections.controller.converter.CollectionItemFormConverter;
import dev.abarmin.smart.collections.controller.model.CollectionItemForm;
import dev.abarmin.smart.collections.entity.CollectionEntity;
import dev.abarmin.smart.collections.entity.CollectionItemEntry;
import dev.abarmin.smart.collections.exception.NoAccessException;
import dev.abarmin.smart.collections.repository.CollectionItemRepository;
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
public class EditCollectionItemController {
    private final SessionService sessionService;
    private final AccessService accessService;
    private final CollectionRepository collectionRepository;
    private final CollectionItemRepository albumRepository;
    private final CollectionItemFormConverter converter;

    @GetMapping("/collections/{id}/albums/add")
    public String createAlbum(@PathVariable("id") int collectionId,
                              Model model) {

        model.addAttribute("form", CollectionItemForm.builder()
                .collectionId(collectionId)
                .build());
        return "collection-item/form";
    }

    @GetMapping("/collections/{collectionId}/albums/{id}")
    public String editAlbum(@PathVariable("id") int albumId,
                            @PathVariable("collectionId") int collectionId,
                            Model model) {
        CollectionEntity collection = collectionRepository.findById(collectionId).orElseThrow();
        UserInfo currentUser = sessionService.getCurrentUser().orElseThrow();
        if (!accessService.hasAccess(currentUser, collection)) {
            throw new NoAccessException();
        }
        CollectionItemEntry album = albumRepository.findById(albumId).orElseThrow();
        model.addAttribute("form", converter.convert(album));

        return "collection-item/form";
    }

    @GetMapping("/collections/{collectionId}/albums/{id}/delete")
    public String deleteAlbum(@PathVariable("id") int albumId,
                            @PathVariable("collectionId") int collectionId,
                            Model model) {
        CollectionEntity collection = collectionRepository.findById(collectionId).orElseThrow();
        UserInfo currentUser = sessionService.getCurrentUser().orElseThrow();
        if (!accessService.hasAccess(currentUser, collection)) {
            throw new NoAccessException();
        }
        CollectionItemEntry album = albumRepository.findById(albumId).orElseThrow();
        album.setDeleted(true);
        albumRepository.save(album);

        return "redirect:/my";
    }

    @PostMapping("/collections/{id}/albums")
    public String saveAlbum(@Valid @ModelAttribute("form") CollectionItemForm form,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "collection-item/form";
        }

        albumRepository.save(converter.convert(form));
        return "redirect:/my";
    }
}

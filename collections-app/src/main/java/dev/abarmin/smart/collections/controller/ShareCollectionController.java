package dev.abarmin.smart.collections.controller;

import dev.abarmin.common.security.service.SessionService;
import dev.abarmin.smart.collections.controller.model.CollectionSharingForm;
import dev.abarmin.smart.collections.entity.CollectionSharingEntity;
import dev.abarmin.smart.collections.entity.CollectionSharingPermissions;
import dev.abarmin.smart.collections.entity.CollectionSharingStatus;
import dev.abarmin.smart.collections.repository.CollectionSharingRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class ShareCollectionController {
    private final CollectionSharingRepository repository;
    private final SessionService sessionService;

    @ModelAttribute("message")
    public String message() {
        return "";
    }

    @ModelAttribute("records")
    public Collection<CollectionSharingEntity> records(@PathVariable("collectionId") int collectionId) {
        return getRecords(collectionId);
    }

    @ModelAttribute("sharingForm")
    public CollectionSharingForm form(@PathVariable("collectionId") int collectionId) {
        return CollectionSharingForm.builder()
                .collectionId(collectionId)
                .role(CollectionSharingPermissions.CHANGE)
                .build();
    }

    @GetMapping("/collections/{collectionId}/share")
    public String view() {
        return "collection/subform-collection-sharing ::subform-collection-sharing";
    }

    @GetMapping("/collections/{collectionId}/share/{shareId}/revoke")
    public String revokeShare(@PathVariable("collectionId") int collectionId,
                              @PathVariable("shareId") int shareId,
                              Model model) {

        repository.findById(shareId)
                .ifPresent(record -> {
                    record.setDeleted(true);
                    repository.save(record);
                });

        model.addAttribute("message", "Access revoked");
        model.addAttribute("records", getRecords(collectionId));

        return "collection/subform-collection-sharing ::subform-collection-sharing";
    }

    @PostMapping("/collections/{collectionId}/share")
    public String shareCollection(Model model,
                                  @ModelAttribute("sharingForm") @Valid CollectionSharingForm form,
                                  BindingResult bindingResult) {

        // check if already shared with this person
        if (isAlreadyShared(form)) {
            bindingResult.addError(new FieldError(
                    "sharingForm",
                    "email",
                    "You have already shared this collection with this recipient"
            ));
        }

        if (bindingResult.hasErrors()) {
            return "collection/subform-collection-sharing ::subform-collection-sharing";
        }

        // create a sharing record & send an email
        repository.save(CollectionSharingEntity.builder()
                .ownerId(AggregateReference.to(sessionService.getCurrentUser().orElseThrow().getId()))
                .collectionId(AggregateReference.to(form.getCollectionId()))
                .permissions(form.getRole())
                .recipientEmail(form.getEmail())
                .status(CollectionSharingStatus.SENT)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build());

        // set message
        model.addAttribute("message", "Invitation sent");
        model.addAttribute("records", getRecords(form.getCollectionId()));

        return "collection/subform-collection-sharing ::subform-collection-sharing";
    }

    private boolean isAlreadyShared(CollectionSharingForm form) {
        int currentUserId = sessionService.getCurrentUser().orElseThrow().getId();
        return !repository
                .getCollectionShares(form.getCollectionId(), currentUserId, form.getEmail())
                .isEmpty();
    }

    private Collection<CollectionSharingEntity> getRecords(int collectionId) {
        int currentUserId = sessionService.getCurrentUser().orElseThrow().getId();
        return repository.getCollectionShares(collectionId, currentUserId);
    }
}

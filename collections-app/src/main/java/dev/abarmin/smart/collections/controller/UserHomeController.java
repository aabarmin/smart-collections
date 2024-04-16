package dev.abarmin.smart.collections.controller;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.service.SessionService;
import dev.abarmin.smart.collections.entity.CollectionEntity;
import dev.abarmin.smart.collections.exception.NoCurrentUserException;
import dev.abarmin.smart.collections.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class UserHomeController {
    private final CollectionRepository collectionRepository;
    private final SessionService sessionService;

    @GetMapping("/my")
    public String myCollections(Model model) {
        UserInfo currentUser = sessionService
                .getCurrentUser()
                .orElseThrow(() -> new NoCurrentUserException());

        Collection<CollectionEntity> collections = collectionRepository.findByUserId(currentUser.getId());
        model.addAttribute("collections", collections);

        return "collection/index";
    }
}

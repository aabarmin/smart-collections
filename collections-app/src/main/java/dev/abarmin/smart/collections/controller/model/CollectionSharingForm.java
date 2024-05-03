package dev.abarmin.smart.collections.controller.model;

import dev.abarmin.smart.collections.entity.CollectionSharingPermissions;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionSharingForm {
    private int collectionId;
    @Email
    @NotEmpty
    private String email;

    @NonNull
    private CollectionSharingPermissions role;
}

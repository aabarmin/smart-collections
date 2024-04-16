package dev.abarmin.smart.collections.controller.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionItemForm {
    private int id;
    @Positive
    private int collectionId;
    @NotEmpty
    private String album;
    @NotEmpty
    private String artist;
}

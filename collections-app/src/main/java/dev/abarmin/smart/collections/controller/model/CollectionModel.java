package dev.abarmin.smart.collections.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class CollectionModel {
    private int id;
    private String name;
    @Builder.Default
    private Collection<CollectionItemModel> albums = new ArrayList<>();
}

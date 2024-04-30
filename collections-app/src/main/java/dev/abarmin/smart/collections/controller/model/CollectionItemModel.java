package dev.abarmin.smart.collections.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionItemModel {
    private int id;
    private String artist;
    private String album;
    private String cover;
}

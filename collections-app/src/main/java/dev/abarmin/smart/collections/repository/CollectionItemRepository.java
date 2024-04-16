package dev.abarmin.smart.collections.repository;

import dev.abarmin.smart.collections.entity.CollectionItemEntry;
import org.springframework.data.repository.CrudRepository;

public interface CollectionItemRepository extends CrudRepository<CollectionItemEntry, Integer> {
}

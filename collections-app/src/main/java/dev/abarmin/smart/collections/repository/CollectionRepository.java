package dev.abarmin.smart.collections.repository;

import dev.abarmin.smart.collections.entity.CollectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface CollectionRepository extends
        PagingAndSortingRepository<CollectionEntity, Integer>,
        CrudRepository<CollectionEntity, Integer> {

    Collection<CollectionEntity> findByUserId(int userId);
}

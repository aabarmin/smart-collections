package dev.abarmin.smart.collections.repository;

import dev.abarmin.smart.collections.entity.CollectionEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface CollectionRepository extends
        PagingAndSortingRepository<CollectionEntity, Integer>,
        CrudRepository<CollectionEntity, Integer> {

    @Query("select * from collections c where c.user_id = :userId and c.deleted = false")
    Collection<CollectionEntity> findByUserId(int userId);
}

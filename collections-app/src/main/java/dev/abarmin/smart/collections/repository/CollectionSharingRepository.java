package dev.abarmin.smart.collections.repository;

import dev.abarmin.smart.collections.entity.CollectionSharingEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CollectionSharingRepository extends CrudRepository<CollectionSharingEntity, Integer> {
    @Query("select * from COLLECTION_SHARING cs where cs.deleted = false and cs.collection_id = :collectionId and cs.owner_id = :ownerId and cs.recipient_email = :recipientEmail")
    Collection<CollectionSharingEntity> getCollectionShares(int collectionId, int ownerId, String recipientEmail);

    @Query("select * from COLLECTION_SHARING cs where cs.deleted = false and cs.collection_id = :collectionId and cs.owner_id = :ownerId")
    Collection<CollectionSharingEntity> getCollectionShares(int collectionId, int ownerId);
}

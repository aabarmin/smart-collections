package dev.abarmin.common.upload.repository;

import dev.abarmin.common.upload.entity.FileEntity;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<FileEntity, Integer> {
}

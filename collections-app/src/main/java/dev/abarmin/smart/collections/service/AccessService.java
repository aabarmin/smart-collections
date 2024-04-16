package dev.abarmin.smart.collections.service;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.smart.collections.entity.CollectionEntity;
import org.springframework.stereotype.Service;

@Service
public class AccessService {
    public boolean hasAccess(UserInfo userInfo, CollectionEntity collection) {
        // dummy implementation, no data sharing so far
        return collection.getUserId().getId() == userInfo.getId();
    }
}

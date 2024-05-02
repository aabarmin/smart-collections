package dev.abarmin.common.security.event;

import dev.abarmin.common.security.domain.UserInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserRegisteredEvent {
    @NonNull
    private UserInfo userInfo;
}

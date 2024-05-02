package dev.abarmin.common.mail.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Envelope {
    @NonNull
    private String to;

    @NonNull
    private String from;

    @NonNull
    private String subject;

    @NonNull
    private String content;
}

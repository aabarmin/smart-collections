package dev.abarmin.common.mail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplate {
    @NonNull
    private String subject;

    @NonNull
    @Builder.Default
    private Collection<String> fields = new ArrayList<>();

    private String template;

    public MailTemplate replace(String field, String value) {
        this.template = StringUtils.replace(template, "${" + field + "}", value);
        return this;
    }
}

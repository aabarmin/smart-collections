package dev.abarmin.common.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("OUTGOING_EMAIL")
public class EnvelopeEntity {
    @Id
    @Column("ID")
    private int id;

    @Column("EMAIL_TO")
    private String to;

    @Column("EMAIL_FROM")
    private String from;

    @Column("EMAIL_SUBJECT")
    private String subject;

    @Column("EMAIL_BODY")
    private String content;

    @Column("STATUS")
    @Builder.Default
    private EnvelopeStatus status = EnvelopeStatus.CREATED;

    @Column("SENDING_ERROR")
    private String sendingError;

    @Column("SEND_ATTEMPT_AT")
    private LocalDateTime sendAttemptAt;

    @Column("SEND_ATTEMPT_NO")
    @Builder.Default
    private int sendAttemptNo = 1;

    @Column("SENT_AT")
    private LocalDateTime sentAt;

    @CreatedDate
    @Builder.Default
    @Column("CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column("UPDATED_AT")
    private LocalDateTime updatedAt;
}

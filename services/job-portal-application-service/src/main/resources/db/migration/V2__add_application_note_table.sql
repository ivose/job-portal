CREATE
    SEQUENCE application_notes_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        application_notes(
            id BIGINT NOT NULL PRIMARY KEY,
            application_id BIGINT NOT NULL,
            added_by_user_id BIGINT NOT NULL,
            content TEXT NOT NULL,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            CONSTRAINT fk_application_notes_application FOREIGN KEY(application_id) REFERENCES applications(id)
        );

CREATE
    INDEX idx_application_notes_application_id ON
    application_notes(application_id);

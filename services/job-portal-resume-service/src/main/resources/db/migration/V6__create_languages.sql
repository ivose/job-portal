CREATE
    SEQUENCE languages_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        languages(
            id BIGINT NOT NULL PRIMARY KEY,
            resume_id BIGINT NOT NULL,
            language_name VARCHAR(255) NOT NULL,
            proficiency VARCHAR(255) NOT NULL,
            display_order INTEGER NOT NULL DEFAULT 0,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            CONSTRAINT fk_languages_resume FOREIGN KEY(resume_id) REFERENCES resumes(id)
        );

CREATE
    INDEX idx_languages_resume_id ON
    languages(resume_id);

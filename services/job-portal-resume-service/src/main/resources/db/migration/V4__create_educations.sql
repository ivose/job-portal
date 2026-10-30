CREATE
    SEQUENCE educations_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        educations(
            id BIGINT NOT NULL PRIMARY KEY,
            resume_id BIGINT NOT NULL,
            institution_name VARCHAR(200) NOT NULL,
            DEGREE VARCHAR(150) NOT NULL,
            field_of_study VARCHAR(150),
            grade VARCHAR(50),
            start_date VARCHAR(255) NOT NULL,
            end_date VARCHAR(255),
            is_current_education BOOLEAN NOT NULL DEFAULT FALSE,
            description VARCHAR(255),
            display_order INTEGER,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            CONSTRAINT fk_educations_resume FOREIGN KEY(resume_id) REFERENCES resumes(id)
        );

CREATE
    INDEX idx_educations_resume_id ON
    educations(resume_id);

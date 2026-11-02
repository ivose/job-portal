CREATE
    SEQUENCE applications_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        applications(
            id BIGINT NOT NULL PRIMARY KEY,
            candidate_id BIGINT NOT NULL,
            job_id BIGINT NOT NULL,
            company_id BIGINT NOT NULL,
            employer_id BIGINT NOT NULL,
            resume_id BIGINT NOT NULL,
            status VARCHAR(255) NOT NULL,
            cover_letter VARCHAR(3000),
            expected_salary NUMERIC(
                38,
                2
            ),
            available_from DATE,
            is_read BOOLEAN DEFAULT FALSE,
            is_starred BOOLEAN DEFAULT FALSE,
            ai_score INTEGER,
            aishort_list_status VARCHAR(255),
            withdrawn_at TIMESTAMP(6),
            applied_at TIMESTAMP(6),
            withrawn_reason VARCHAR(255),
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL
        );

CREATE
    INDEX idx_applications_candidate_id ON
    applications(candidate_id);

CREATE
    INDEX idx_applications_job_id ON
    applications(job_id);

CREATE
    INDEX idx_applications_company_id ON
    applications(company_id);

CREATE
    INDEX idx_applications_employer_id ON
    applications(employer_id);

CREATE
    SEQUENCE saved_jobs_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        saved_jobs(
            id BIGINT NOT NULL PRIMARY KEY,
            candidate_id BIGINT NOT NULL,
            job_id BIGINT NOT NULL,
            saved_at TIMESTAMP(6) NOT NULL,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            CONSTRAINT uq_saved_jobs_candidate_job UNIQUE(
                candidate_id,
                job_id
            )
        );

CREATE
    INDEX idx_saved_jobs_candidate_id ON
    saved_jobs(candidate_id);

CREATE
    SEQUENCE work_experiences_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        work_experiences(
            id BIGINT NOT NULL PRIMARY KEY,
            resume_id BIGINT NOT NULL,
            company_name VARCHAR(255) NOT NULL,
            company_logo_url VARCHAR(255),
            job_title VARCHAR(255) NOT NULL,
            employment_type VARCHAR(255),
            location VARCHAR(255),
            start_date DATE NOT NULL,
            end_date DATE,
            is_current_job BOOLEAN NOT NULL DEFAULT FALSE,
            description VARCHAR(255),
            display_order INTEGER NOT NULL DEFAULT 0,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            CONSTRAINT fk_work_experiences_resume FOREIGN KEY(resume_id) REFERENCES resumes(id)
        );

CREATE
    TABLE
        work_experience_technologies(
            work_experience_id BIGINT NOT NULL,
            technologies VARCHAR(255),
            CONSTRAINT fk_work_experience_technologies FOREIGN KEY(work_experience_id) REFERENCES work_experiences(id)
        );

CREATE
    INDEX idx_work_experiences_resume_id ON
    work_experiences(resume_id);

/*
useful squeies:
docker exec job-portal-postgres psql -U postgres -d yt_job_portal_resume -v ON_ERROR_STOP=1 -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"
docker exec job-portal-postgres psql -U postgres -d yt_job_portal_resume -c "SELECT version, description, checksum, success FROM flyway_schema_history ORDER BY installed_rank;"
*/

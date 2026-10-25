-- Job Service: yt_job_portal_job
-- GenerationType.AUTO + Long on PostgreSQL: entity-specific sequences.
CREATE
    SEQUENCE jobs_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    SEQUENCE jobs_categories_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    SEQUENCE job_skills_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    SEQUENCE job_tags_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        jobs_categories(
            id BIGINT NOT NULL PRIMARY KEY,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            name VARCHAR(255) NOT NULL UNIQUE,
            slug VARCHAR(255) UNIQUE,
            description VARCHAR(255),
            icon_url VARCHAR(255),
            parent_id BIGINT,
            active BOOLEAN DEFAULT TRUE,
            CONSTRAINT fk_jobs_categories_parent FOREIGN KEY(parent_id) REFERENCES jobs_categories(id)
        );

CREATE
    TABLE
        job_skills(
            id BIGINT NOT NULL PRIMARY KEY,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            name VARCHAR(255) NOT NULL UNIQUE,
            slug VARCHAR(255) UNIQUE, -- SkillCategory currently has no @Enumerated: ordinal persistence.
            category SMALLINT,
            active BOOLEAN NOT NULL DEFAULT TRUE
        );

CREATE
    TABLE
        job_tags(
            id BIGINT NOT NULL PRIMARY KEY,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            name VARCHAR(255) NOT NULL UNIQUE,
            slug VARCHAR(255) UNIQUE,
            active BOOLEAN NOT NULL DEFAULT TRUE
        );

CREATE
    TABLE
        jobs(
            id BIGINT NOT NULL PRIMARY KEY,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            employer_id BIGINT NOT NULL,
            company_id BIGINT NOT NULL,
            title VARCHAR(255) NOT NULL,
            description VARCHAR(255) NOT NULL,
            requirements VARCHAR(255) NOT NULL,
            responsibilities VARCHAR(255) NOT NULL,
            benefits VARCHAR(255) NOT NULL,
            catecory_id BIGINT,
            address VARCHAR(255),
            city VARCHAR(255),
            state VARCHAR(255),
            country VARCHAR(255),
            zip_code VARCHAR(255),
            min_salary NUMERIC(
                38,
                2
            ),
            max_salary NUMERIC(
                38,
                2
            ),
            job_type VARCHAR(255) NOT NULL,
            work_mode VARCHAR(255) NOT NULL,
            experience_level VARCHAR(255) NOT NULL,
            job_status VARCHAR(255) NOT NULL,
            openings INTEGER,
            application_count INTEGER,
            application_dead_line DATE,
            expired_at DATE,
            active BOOLEAN,
            published_at TIMESTAMP(6),
            closed_at TIMESTAMP(6),
            CONSTRAINT fk_jobs_category FOREIGN KEY(catecory_id) REFERENCES jobs_categories(id)
        );

-- Default join tables for Job.skills and Job.tags.
CREATE
    TABLE
        jobs_skills(
            job_id BIGINT NOT NULL,
            skills_id BIGINT NOT NULL,
            PRIMARY KEY(
                job_id,
                skills_id
            ),
            CONSTRAINT fk_jobs_skills_job FOREIGN KEY(job_id) REFERENCES jobs(id),
            CONSTRAINT fk_jobs_skills_skill FOREIGN KEY(skills_id) REFERENCES job_skills(id)
        );

CREATE
    TABLE
        jobs_tags(
            job_id BIGINT NOT NULL,
            tags_id BIGINT NOT NULL,
            PRIMARY KEY(
                job_id,
                tags_id
            ),
            CONSTRAINT fk_jobs_tags_job FOREIGN KEY(job_id) REFERENCES jobs(id),
            CONSTRAINT fk_jobs_tags_tag FOREIGN KEY(tags_id) REFERENCES job_tags(id)
        );

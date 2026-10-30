-- Resume Service initial schema
CREATE
    SEQUENCE resumes_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        resumes(
            id BIGINT NOT NULL PRIMARY KEY,
            candidate_id BIGINT NOT NULL,
            title VARCHAR(255) NOT NULL,
            template VARCHAR(255) NOT NULL,
            visibility VARCHAR(255) NOT NULL,
            is_default BOOLEAN NOT NULL DEFAULT FALSE,
            first_name VARCHAR(255),
            last_name VARCHAR(255),
            headline VARCHAR(255),
            email VARCHAR(255),
            phone VARCHAR(255),
            city VARCHAR(255),
            country VARCHAR(255),
            linkedin_url VARCHAR(255),
            github_url VARCHAR(255),
            portfolio_url VARCHAR(255),
            website_url VARCHAR(255),
            summary VARCHAR(255),
            completion_score INTEGER,
            is_active BOOLEAN NOT NULL DEFAULT TRUE,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL
        );

CREATE
    INDEX idx_resumes_candidate_id ON
    resumes(candidate_id);

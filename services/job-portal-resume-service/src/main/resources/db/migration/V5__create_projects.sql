CREATE
    SEQUENCE projects_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        projects(
            id BIGINT NOT NULL PRIMARY KEY,
            resume_id BIGINT NOT NULL,
            title VARCHAR(255) NOT NULL,
            description VARCHAR(255) NOT NULL,
            project_url VARCHAR(255),
            source_code_url VARCHAR(255),
            start_date VARCHAR(255) NOT NULL,
            end_date VARCHAR(255),
            ison_goeing BOOLEAN NOT NULL DEFAULT FALSE,
            display_order INTEGER,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            CONSTRAINT fk_projects_resume FOREIGN KEY(resume_id) REFERENCES resumes(id)
        );

CREATE
    TABLE
        project_technologies(
            project_id BIGINT NOT NULL,
            technologies VARCHAR(255),
            CONSTRAINT fk_project_technologies FOREIGN KEY(project_id) REFERENCES projects(id)
        );

CREATE
    INDEX idx_projects_resume_id ON
    projects(resume_id);

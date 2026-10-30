CREATE
    SEQUENCE resume_skills_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        resume_skills(
            id BIGINT NOT NULL PRIMARY KEY,
            resume_id BIGINT NOT NULL,
            skill_name VARCHAR(255) NOT NULL,
            proficiency_level VARCHAR(255),
            years_of_experience INTEGER,
            display_order INTEGER NOT NULL DEFAULT 0,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            CONSTRAINT fk_resume_skills_resume FOREIGN KEY(resume_id) REFERENCES resumes(id)
        );

CREATE
    INDEX idx_resume_skills_resume_id ON
    resume_skills(resume_id);

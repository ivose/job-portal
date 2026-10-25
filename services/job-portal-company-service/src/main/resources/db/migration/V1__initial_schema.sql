-- Company Service: yt_job_portal_company
CREATE
    SEQUENCE companies_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        companies(
            id BIGINT NOT NULL PRIMARY KEY,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            name VARCHAR(255) UNIQUE,
            slug VARCHAR(255),
            tagline VARCHAR(255),
            description VARCHAR(255),
            logo_url VARCHAR(255),
            cover_image_url VARCHAR(255),
            website VARCHAR(255),
            email VARCHAR(255),
            phone VARCHAR(255),
            founded_year INTEGER,
            company_size VARCHAR(255),
            company_type VARCHAR(255),
            industry_type VARCHAR(255),
            status VARCHAR(255),
            is_verified BOOLEAN NOT NULL DEFAULT FALSE,
            registration_number VARCHAR(255) NOT NULL UNIQUE,
            owner_id BIGINT NOT NULL UNIQUE,
            active BOOLEAN DEFAULT TRUE
        );

-- Default @ElementCollection table and columns for Company.socialLinks.
-- SocialPlatform has no @Enumerated, so Hibernate stores its enum ordinal.
CREATE
    TABLE
        company_social_links(
            company_id BIGINT NOT NULL,
            platform SMALLINT,
            url VARCHAR(255),
            CONSTRAINT fk_company_social_links_company FOREIGN KEY(company_id) REFERENCES companies(id)
        );

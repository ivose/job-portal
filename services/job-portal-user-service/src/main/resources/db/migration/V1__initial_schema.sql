-- User Service: yt_job_portal_user
-- BaseTable uses GenerationType.AUTO (PostgreSQL sequences).
CREATE
    SEQUENCE users_seq
START WITH
    1 INCREMENT BY 50;

CREATE
    TABLE
        users(
            id BIGINT NOT NULL PRIMARY KEY,
            created_at TIMESTAMP(6) NOT NULL,
            updated_at TIMESTAMP(6) NOT NULL,
            full_name VARCHAR(255) NOT NULL,
            password VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL UNIQUE,
            phone VARCHAR(255),
            profile_image VARCHAR(255),
            ROLE VARCHAR(255) NOT NULL,
            status VARCHAR(255) NOT NULL,
            last_login TIMESTAMP(6),
            suspended_at TIMESTAMP(6),
            deleted_at TIMESTAMP(6)
        );

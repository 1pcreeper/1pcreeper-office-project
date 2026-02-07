DROP TABLE IF EXISTS abstract_auditable_entity CASCADE;
DROP TABLE IF EXISTS abstract_persistable_entity CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;


CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(20) NOT NULL UNIQUE CHECK (name <> '' AND name NOT LIKE '% %' AND name ~ '^[a-zA-Z0-9@._]+$'),
  password TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE abstract_persistable_entity (
    id SERIAL PRIMARY KEY,
    version INTEGER
);

CREATE TABLE abstract_auditable_entity (
  id SERIAL PRIMARY KEY,
  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP,
  created_by INTEGER ,
  updated_by INTEGER ,
  FOREIGN KEY (created_by) REFERENCES "users"(id) ON DELETE CASCADE,
  FOREIGN KEY (updated_by) REFERENCES "users"(id) ON DELETE CASCADE
) INHERITS (abstract_persistable_entity);



INSERT INTO users(id,name,password,created_at) VALUES(0,'admin','$2a$10$L48wRTygNoYcqJs6nYPL5uQuvHFD8fDv9AG1a3yHvsTJAnTGK2DsK',now());
-- admin password
-- A6NmR7UKtE00tkXM9ryVGECqjHZGhx9ifTpYdPuooWpoY1Nrj3pfe88W75CWnipqTiDxav7E
-- A6NmR7UKtE00tkXM9ryVGECqjHZGhx9ifTpYdPuooWpoY1Nrj3pfe88W75CWnipqTiDxav7E

INSERT INTO user_roles(user_id,role) VALUES(0,'ADMIN');
INSERT INTO user_roles(user_id,role) VALUES(0,'USER');
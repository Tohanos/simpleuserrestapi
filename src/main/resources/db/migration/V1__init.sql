CREATE TABLE "users" (
                         "id" SERIAL PRIMARY KEY,
                         "nickname" VARCHAR(255) UNIQUE,
                         "name" VARCHAR(255),
                         "surname1" VARCHAR(255),
                         "surname2" VARCHAR(255),
                         "birthday" DATE,
                         "email" VARCHAR(255),
                         "phone" VARCHAR(255),
                         "password" VARCHAR(255)
);


CREATE TABLE "users" (
                         "id" SERIAL PRIMARY KEY,
                         "username" VARCHAR(255) UNIQUE,
                         "password" VARCHAR(255)
);

CREATE TABLE "messages" (
                        "id" SERIAL PRIMARY KEY,
                        "user_id" int,
                        "text" VARCHAR(255),
                        "dt" timestamp
);

ALTER TABLE "messages" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

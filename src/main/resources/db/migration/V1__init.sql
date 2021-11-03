CREATE TABLE NOTE (
    id serial primary key not null ,
    title varchar not null,
    content varchar not null,
    created_at timestamp not null,
    updated_at timestamp not null
)
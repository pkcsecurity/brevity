CREATE TABLE article (
    id bigserial primary key,
    title text NOT NULL,
    content text NOT NULL,
    date_added timestamp default now()
);

INSERT INTO article (title, content) VALUES
    ('Great article', 'Actually this article doesn`t say much'),
    ('Better article', 'Just kidding :P');
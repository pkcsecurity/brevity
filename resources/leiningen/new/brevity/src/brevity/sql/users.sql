-- :name insert-user
-- :command :execute
insert into users (password_hash, full_name, email)
values (:password-hash, :full-name, :email);

-- :name user-by-email
-- :result :one
select * from users
where email = :email;

-- :name session-by-id
-- :result :one
select * from sessions
  natural join users
where session_id = :id;

-- :name insert-session
-- :command :execute
insert into sessions (session_id, user_id)
values (:id, :user-id);

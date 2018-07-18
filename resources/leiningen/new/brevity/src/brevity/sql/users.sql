-- :name insert-user
insert into users (password_hash, full_name, email)
values (:password-hash, :full-name, :email);

-- :name session-by-id
select * from sessions
  natural join users
where session_id = :id;

-- :name insert-session
insert into sessions (id, user_id)
values (:id, :user-id);
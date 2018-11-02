-- :name insert-user
-- :command :execute
insert into users (password_hash, full_name, email, is_admin)
values (:passwordhash, :full-name, :email, :is-admin);

-- :name user-by-email
-- :result :one
select * from users
where email = :email;

-- :name session-by-id
-- :result :one
select
  *,
  extract(epoch from now() - started) / 60 as since_started,
  extract(epoch from now() - last_active) / 60 as since_active
from sessions
  natural join users
where session_id = :id;

-- :name keep-session-active
-- :command :execute
update sessions set last_active = now() where session_id = :id;

-- :name insert-session
-- :command :execute
insert into sessions (session_id, user_id)
values (:id, :user-id);

-- :name delete-session
-- :command :execute
delete from sessions where session_id = :id;

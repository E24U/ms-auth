create table users_roles
(
    user_id uuid references users (id),
    role_id uuid references roles (id)
);
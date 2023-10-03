
create table if not exists blacklisttokens (
  id varchar(255) not null,
  created_at timestamp(6),
  primary key (id)
);

create table if not exists reset_tokens (
  id varchar(255) not null,
  email varchar(255),
  primary key (id)
);

create table if not exists users (
  id uuid not null,
  email varchar(255),
  full_name varchar(255),
  password varchar(255),
  role varchar(255) check (role in ('USER','ADMIN')),
  primary key (id)
);
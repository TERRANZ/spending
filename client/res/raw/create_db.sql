CREATE TABLE types (
  _id INTEGER PRIMARY KEY,
  name text,
  server_id integer
);

CREATE TABLE tr (
  _id INTEGER PRIMARY KEY,
  t_date integer,
  t_money text,
  t_type integer,
  t_comment text,
  t_serverid integer default -1,
  foreign key (t_type) references types(_id)
);
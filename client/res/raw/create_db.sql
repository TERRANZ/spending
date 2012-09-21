CREATE TABLE types (
  _id INTEGER PRIMARY KEY,
  name text
);

CREATE TABLE tr (
  _id INTEGER PRIMARY KEY,
  t_date integer,
  t_money text,
  t_type integer,
  t_comment text,
  foreign key (t_type) references types(_id)
);
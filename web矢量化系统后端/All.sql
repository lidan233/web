create  table admin_user (
  id INTEGER  primary key,
  name varchar(100) ,
  account varchar(100) ,
  passwd varchar(100)
);
insert into admin_user values(0,'root','123456','123456') ;

alter table all_user add  column  account varchar(100) unique  ;
create table project_user(
  id INTEGER  primary key ,
  name varchar(100) ,
  account varchar(100) ,
  passwd varchar(100)

);

insert into  project_user values(0,'lidan','123456','123456') ;
create table projects_users_relation(
  id INTEGER  ,
  project_id INTEGER  ,
  project_user_id INTEGER,
   constraint  project_user_id foreign key(project_id) references project_user(id),
   constraint  project_id foreign  key(project_id) references  project(id)
);


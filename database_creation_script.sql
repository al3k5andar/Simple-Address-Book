
create table address (id bigint not null auto_increment, city varchar(255), country varchar(255), nouse_no varchar(255), street varchar(255), primary key (id)) engine=InnoDB;
create table contact (id bigint not null auto_increment, contact_type varchar(255), first_name varchar(45), image longblob, last_name varchar(45), middle_name varchar(255), details_id bigint not null, note_id bigint, primary key (id)) engine=InnoDB;
create table details (id bigint not null auto_increment, birthday date, email varchar(255), nickname varchar(255), phone varchar(13) not null, work_phone varchar(255), address_id bigint, primary key (id)) engine=InnoDB;
create table group_contact (group_id bigint not null, contact_id bigint not null, primary key (contact_id, group_id)) engine=InnoDB;
create table group_of_contacts (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table note (id bigint not null auto_increment, description longtext, primary key (id)) engine=InnoDB;
alter table contact add constraint FKq6u4851dfypgn3mmn9i876nqs foreign key (details_id) references details (id);
alter table contact add constraint FKowidydwdlqnuruqts0quwgbdi foreign key (note_id) references note (id);
alter table details add constraint FKkmxk0dpw7981py2lteah2jkhs foreign key (address_id) references address (id);
alter table group_contact add constraint FK6e1ndgukul60i5ubvq510oajv foreign key (contact_id) references contact (id);
alter table group_contact add constraint FKqqv7xosibfi7ggllna1sh9w95 foreign key (group_id) references group_of_contacts (id);

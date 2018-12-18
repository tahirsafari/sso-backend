create table customer (id bigint not null auto_increment, address varchar(255), contact_number varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
create table failed_auth_log (id bigint not null auto_increment, attempts integer not null, last_attempt_on datetime(6), user_id bigint not null, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, created_on datetime(6), expires_on datetime(6), token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table permission (authority varchar(255) not null, description varchar(255), primary key (authority)) engine=InnoDB
create table privilege (id bigint not null auto_increment, description varchar(255), enabled bit not null, menu_group varchar(255), module varchar(255), name varchar(255), url varchar(255), primary key (id)) engine=InnoDB
create table role (id bigint not null auto_increment, authority varchar(255) not null, description varchar(255) not null, enabled bit not null, primary key (id)) engine=InnoDB
create table role_permission (role_id bigint not null, permission_id varchar(255) not null) engine=InnoDB
create table user (id bigint not null auto_increment, account_non_locked bit not null, credentials_non_expired bit not null, enabled bit not null, password varchar(255), password_changed_date datetime(6), username varchar(255), primary key (id)) engine=InnoDB
create table user_role (user_id bigint not null, role_id bigint not null) engine=InnoDB
alter table role_permission add constraint UKt49nxpqax9cveurs8f61sns2d unique (role_id, permission_id)
alter table failed_auth_log add constraint FK4u44niffg3wu1jitx89yoi6lu foreign key (user_id) references user (id)
alter table password_reset_token add constraint FK5lwtbncug84d4ero33v3cfxvl foreign key (user_id) references user (id)
alter table role_permission add constraint FKf8yllw1ecvwqy3ehyxawqa1qp foreign key (permission_id) references permission (authority)
alter table role_permission add constraint FKa6jx8n8xkesmjmv6jqug6bg68 foreign key (role_id) references role (id)
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id)
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id)
create table customer (id bigint not null auto_increment, address varchar(255), contact_number varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
create table failed_auth_log (id bigint not null auto_increment, attempts integer not null, last_attempt_on datetime(6), user_id bigint not null, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, created_on datetime(6), expires_on datetime(6), token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table permission (authority varchar(255) not null, description varchar(255), primary key (authority)) engine=InnoDB
create table privilege (id bigint not null auto_increment, description varchar(255), enabled bit not null, menu_group varchar(255), module varchar(255), name varchar(255), url varchar(255), primary key (id)) engine=InnoDB
create table role (id bigint not null auto_increment, authority varchar(255) not null, description varchar(255) not null, enabled bit not null, primary key (id)) engine=InnoDB
create table role_permission (role_id bigint not null, permission_id varchar(255) not null) engine=InnoDB
create table user (id bigint not null auto_increment, account_non_locked bit not null, credentials_non_expired bit not null, enabled bit not null, password varchar(255), password_changed_date datetime(6), username varchar(255), primary key (id)) engine=InnoDB
create table user_role (user_id bigint not null, role_id bigint not null) engine=InnoDB
alter table role_permission add constraint UKt49nxpqax9cveurs8f61sns2d unique (role_id, permission_id)
alter table failed_auth_log add constraint FK4u44niffg3wu1jitx89yoi6lu foreign key (user_id) references user (id)
alter table password_reset_token add constraint FK5lwtbncug84d4ero33v3cfxvl foreign key (user_id) references user (id)
alter table role_permission add constraint FKf8yllw1ecvwqy3ehyxawqa1qp foreign key (permission_id) references permission (authority)
alter table role_permission add constraint FKa6jx8n8xkesmjmv6jqug6bg68 foreign key (role_id) references role (id)
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id)
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id)
create table customer (id bigint not null auto_increment, address varchar(255), contact_number varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
create table failed_auth_log (id bigint not null auto_increment, attempts integer not null, last_attempt_on datetime(6), user_id bigint not null, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, created_on datetime(6), expires_on datetime(6), token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table permission (authority varchar(255) not null, description varchar(255), primary key (authority)) engine=InnoDB
create table privilege (id bigint not null auto_increment, description varchar(255), enabled bit not null, menu_group varchar(255), module varchar(255), name varchar(255), url varchar(255), primary key (id)) engine=InnoDB
create table role (id bigint not null auto_increment, authority varchar(255) not null, description varchar(255) not null, enabled bit not null, primary key (id)) engine=InnoDB
create table role_permission (role_id bigint not null, permission_id varchar(255) not null) engine=InnoDB
create table user (id bigint not null auto_increment, account_non_locked bit not null, credentials_non_expired bit not null, enabled bit not null, password varchar(255), password_changed_date datetime(6), username varchar(255), primary key (id)) engine=InnoDB
create table user_role (user_id bigint not null, role_id bigint not null) engine=InnoDB
alter table role_permission add constraint UKt49nxpqax9cveurs8f61sns2d unique (role_id, permission_id)
alter table failed_auth_log add constraint FK4u44niffg3wu1jitx89yoi6lu foreign key (user_id) references user (id)
alter table password_reset_token add constraint FK5lwtbncug84d4ero33v3cfxvl foreign key (user_id) references user (id)
alter table role_permission add constraint FKf8yllw1ecvwqy3ehyxawqa1qp foreign key (permission_id) references permission (authority)
alter table role_permission add constraint FKa6jx8n8xkesmjmv6jqug6bg68 foreign key (role_id) references role (id)
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id)
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id)
create table customer (id bigint not null auto_increment, address varchar(255), contact_number varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
create table failed_auth_log (id bigint not null auto_increment, attempts integer not null, last_attempt_on datetime(6), user_id bigint not null, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, created_on datetime(6), expires_on datetime(6), token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table permission (authority varchar(255) not null, description varchar(255), primary key (authority)) engine=InnoDB
create table privilege (id bigint not null auto_increment, description varchar(255), enabled bit not null, menu_group varchar(255), module varchar(255), name varchar(255), url varchar(255), primary key (id)) engine=InnoDB
create table role (id bigint not null auto_increment, authority varchar(255) not null, description varchar(255) not null, enabled bit not null, primary key (id)) engine=InnoDB
create table role_permission (role_id bigint not null, permission_id varchar(255) not null) engine=InnoDB
create table user (id bigint not null auto_increment, account_non_locked bit not null, credentials_non_expired bit not null, enabled bit not null, password varchar(255), password_changed_date datetime(6), username varchar(255), primary key (id)) engine=InnoDB
create table user_role (user_id bigint not null, role_id bigint not null) engine=InnoDB
alter table role_permission add constraint UKt49nxpqax9cveurs8f61sns2d unique (role_id, permission_id)
alter table failed_auth_log add constraint FK4u44niffg3wu1jitx89yoi6lu foreign key (user_id) references user (id)
alter table password_reset_token add constraint FK5lwtbncug84d4ero33v3cfxvl foreign key (user_id) references user (id)
alter table role_permission add constraint FKf8yllw1ecvwqy3ehyxawqa1qp foreign key (permission_id) references permission (authority)
alter table role_permission add constraint FKa6jx8n8xkesmjmv6jqug6bg68 foreign key (role_id) references role (id)
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id)
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id)
create table customer (id bigint not null auto_increment, address varchar(255), contact_number varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
create table failed_auth_log (id bigint not null auto_increment, attempts integer not null, last_attempt_on datetime(6), user_id bigint not null, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, created_on datetime(6), expires_on datetime(6), token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table permission (authority varchar(255) not null, description varchar(255), primary key (authority)) engine=InnoDB
create table privilege (id bigint not null auto_increment, description varchar(255), enabled bit not null, menu_group varchar(255), module varchar(255), name varchar(255), url varchar(255), primary key (id)) engine=InnoDB
create table role (id bigint not null auto_increment, authority varchar(255) not null, description varchar(255) not null, enabled bit not null, primary key (id)) engine=InnoDB
create table role_permission (role_id bigint not null, permission_id varchar(255) not null) engine=InnoDB
create table user (id bigint not null auto_increment, account_non_locked bit not null, credentials_non_expired bit not null, enabled bit not null, password varchar(255), password_changed_date datetime(6), username varchar(255), primary key (id)) engine=InnoDB
create table user_role (user_id bigint not null, role_id bigint not null) engine=InnoDB
alter table role_permission add constraint UKt49nxpqax9cveurs8f61sns2d unique (role_id, permission_id)
alter table failed_auth_log add constraint FK4u44niffg3wu1jitx89yoi6lu foreign key (user_id) references user (id)
alter table password_reset_token add constraint FK5lwtbncug84d4ero33v3cfxvl foreign key (user_id) references user (id)
alter table role_permission add constraint FKf8yllw1ecvwqy3ehyxawqa1qp foreign key (permission_id) references permission (authority)
alter table role_permission add constraint FKa6jx8n8xkesmjmv6jqug6bg68 foreign key (role_id) references role (id)
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id)
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id)
create table customer (id bigint not null auto_increment, address varchar(255), contact_number varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
create table failed_auth_log (id bigint not null auto_increment, attempts integer not null, last_attempt_on datetime(6), user_id bigint not null, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, created_on datetime(6), expires_on datetime(6), token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table permission (authority varchar(255) not null, description varchar(255), primary key (authority)) engine=InnoDB
create table privilege (id bigint not null auto_increment, description varchar(255), enabled bit not null, menu_group varchar(255), module varchar(255), name varchar(255), url varchar(255), primary key (id)) engine=InnoDB
create table role (id bigint not null auto_increment, authority varchar(255) not null, description varchar(255) not null, enabled bit not null, primary key (id)) engine=InnoDB
create table role_permission (role_id bigint not null, permission_id varchar(255) not null) engine=InnoDB
create table user (id bigint not null auto_increment, account_non_locked bit not null, credentials_non_expired bit not null, enabled bit not null, password varchar(255), password_changed_date datetime(6), username varchar(255), primary key (id)) engine=InnoDB
create table user_role (user_id bigint not null, role_id bigint not null) engine=InnoDB
alter table role_permission add constraint UKt49nxpqax9cveurs8f61sns2d unique (role_id, permission_id)
alter table failed_auth_log add constraint FK4u44niffg3wu1jitx89yoi6lu foreign key (user_id) references user (id)
alter table password_reset_token add constraint FK5lwtbncug84d4ero33v3cfxvl foreign key (user_id) references user (id)
alter table role_permission add constraint FKf8yllw1ecvwqy3ehyxawqa1qp foreign key (permission_id) references permission (authority)
alter table role_permission add constraint FKa6jx8n8xkesmjmv6jqug6bg68 foreign key (role_id) references role (id)
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id)
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id)

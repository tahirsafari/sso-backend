INSERT INTO permission VALUES('CREATE_A_USER', 'Create a user');
INSERT INTO permission VALUES('VIEW_ALL_USERS', 'View all users');
INSERT INTO permission VALUES('VIEW_A_USER', 'View a user');
INSERT INTO permission VALUES('UPDATE_A_USER', 'Update a user');
INSERT INTO permission VALUES('CREATE_A_ROLE', 'Create a role');
INSERT INTO permission VALUES('VIEW_ALL_ROLES', 'View all roles');
INSERT INTO permission VALUES('VIEW_A_ROLE', 'View a role');
INSERT INTO permission VALUES('UPDATE_A_ROLE', 'Update a role');

INSERT INTO role_permission VALUES(1,'CREATE_A_USER');
INSERT INTO role_permission VALUES(1,'VIEW_ALL_USERS');
INSERT INTO role_permission VALUES(1,'VIEW_A_USER');
INSERT INTO role_permission VALUES(1,'UPDATE_A_USER');
INSERT INTO role_permission VALUES(1,'CREATE_A_ROLE');
INSERT INTO role_permission VALUES(1,'VIEW_ALL_ROLES');
INSERT INTO role_permission VALUES(1,'VIEW_A_ROLE');
INSERT INTO role_permission VALUES(1,'UPDATE_A_ROLE');


INSERT INTO user_role VALUES(1,1);
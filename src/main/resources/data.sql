insert into users(fullname, username, password) values
('Ivanov Ivan', 'admin', '$2a$10$CJGzLmMOUdoqAnrWaMgcnOfLSD135khKgJlN.dmzrAvSBR4rH.fOa'),
('Petrov Ivan', 'student', '$2a$10$bUwJL2vr7RO0PIOdfmEIDeIwWClreTsuMW9qgomzb7MWMhDN/P7F6');

insert into user_role(user_id, roles) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_STUDENT');

insert into courses(name, author, content) values
('Computer science', 'Ivan Ivanov', 'Content....'),
('Management', 'Petrov Ivanov', 'Content....');

insert into course_of_student(user_id, course_id, result) values
(2, 1, 5),
(2, 2, null);
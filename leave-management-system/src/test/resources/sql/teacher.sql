insert into account(name, role, email, password ) values ('Maung Maung','Teacher','maung@gmail.com','pass');
insert into account(name, role, email, password ) values ('Aung Aung','Teacher','aung@gmail.com','pass');
insert into account(name, role, email, password ) values ('Ba Maung','Teacher','bamaung@gmail.com','pass');

insert into teacher values (1,'09254295287','2022-09-17');
insert into teacher values (2,'09854715477','2022-08-17');
insert into teacher values (3,'09568754214','2022-07-17');

insert into classes(teacher_id,start_date,months,description) values(1,'2022-09-18',3,'SpringFramework');
insert into classes(teacher_id,start_date,months,description) values(1,'2022-09-18',3,'SpringBoot');
insert into classes(teacher_id,start_date,months,description) values(2,'2022-09-11',3,'Flutter');
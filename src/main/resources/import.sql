#INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (1, 'ANDRÉS', 'ASTORGA', 'ANDRES.ASTORGA@MAYOR.CL', '2022-11-11');
#INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (2, 'BÁRBARA', 'HERMOSILLA', 'BARBIHERMOSILLA.N@GMAIL.COM', '2022-11-11');

INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-01','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('John', 'Smith', 'john.smith@gmail.com', '2017-08-22','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25','');


INSERT INTO productos (nombre, precio, create_at) VALUES('Teclado redragon KUMARA', 45000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Monitor GIGABYTE 24', 220000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('CAFE DE GRANO MOLIDO STARBUCKS BLACK', 8990, NOW());


INSERT INTO users (username, password, enabled) VALUES ('andres', '$2a$10$UEKhp0Z4K9/XW.3S2By9KuIHgJe94r.zhejN/OTKPAqRsX/.wpqKG',1);
INSERT INTO users (username, password, enabled) VALUES ('mati', '$2a$10$YxLXK3j.A7xvm931qakd/elJWERaNfjy8Jl2qUd6VGCURpyCjTh/q',1);


INSERT INTO authorities (authority, user_id) VALUES ('ROLE_USER', 1);
INSERT INTO authorities (authority, user_id) VALUES ('ROLE_ADMIN', 1);
INSERT INTO authorities (authority, user_id) VALUES ('ROLE_USER', 2);
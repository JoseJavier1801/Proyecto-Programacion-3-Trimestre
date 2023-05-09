create database tienda;
use tienda;

create table usuarios(
Primary Key (id_u),
id_u int auto_increment not null,
nombre_usuario varchar(150),
contraseña_usuario varchar(150),
correo_usuario varchar (150),
dni varchar(50)
);

create table administrador(
Primary Key (id_a) ,
id_a int auto_increment not null,
nombre_admin varchar(150),
contraseña_admin varchar(150),
correo_admin varchar (150),
dni varchar(50)
);

create table productos(
Primary Key(id_p),
id_p int auto_increment not null,
nombre_producto varchar(150),
descripcion varchar(150),
stock int,
precio double,
id_admin int not null
);

Create table carrito(
id_u  int  not null,
id_p int not null,
fecha date,
cantidad int,
precio double,
Primary Key(id_u,id_p)
);

alter table productos add foreign key(id_admin) references administrador(id_a);
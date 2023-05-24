create database tienda;
use tienda;

create table administrador(
Primary Key (id_a) ,
id_a int auto_increment not null,
nombre_admin varchar(150),
contraseña_admin varchar(150),
correo_admin varchar (150),
dni varchar(50)
);

create table usuarios(
Primary Key (id_u),
id_u int auto_increment not null,
nombre_usuario varchar(150),
contraseña_usuario varchar(150),
correo_usuario varchar (150),
dni varchar(50)
);

create table productos(
Primary Key(id_p),
id_p int auto_increment not null,
nombre_producto varchar(150),
descripcion varchar(150),
stock int,
precio double,
id_admin int
);
alter table productos add foreign key(id_admin) references administrador(id_a) on delete set null;

CREATE TABLE Carrito (
  id_carrito int auto_increment,
  id_usuario INT NOT NULL,
  id_producto INT NOT NULL,
  fecha_compra DATE NOT NULL,
  nombreProducto varchar(150),
  cantidad INT NOT NULL,
  precio DOUBLE NOT NULL,
  PRIMARY KEY (id_carrito)

);

alter table Carrito add foreign key(id_usuario) references usuarios(id_u);
alter table Carrito add foreign key(id_producto) references productos(id_p);



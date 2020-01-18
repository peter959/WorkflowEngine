DROP TABLE IF EXISTS risorsa_entity;
CREATE TABLE risorsa_entity(
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nation VARCHAR(250) NOT NULL,
  iva INT NOT NULL,
  port INT(250) DEFAULT NULL
);

insert into risorsa_entity(id,nation,iva,port) 
values(10001,'IT',22,0);
insert into risorsa_entity(id,nation,iva,port) 
values(10002,'FR',20,0);
insert into risorsa_entity(id,nation,iva,port) 
values(10003,'DE',19,0);
insert into risorsa_entity(id,nation,iva,port) 
values(10004,'BE',21,0);
-- password '123' encripted -> '{bcrypt}$2a$10$Du0cfJx6hVx32g30grRZve5kcocChzloRoTSpJhVOaG.296Ng6zFy'
--Clients
--(idCliente,username,password,name,2name,email,directions,phonenumber,image,status,TIPO);
insert into client values (1,'dbelda','{bcrypt}$2a$10$Du0cfJx6hVx32g30grRZve5kcocChzloRoTSpJhVOaG.296Ng6zFy','david','belda','davidbelda7@gmail.com',  'micasa_micasa2', '686169256', 'https://via.placeholder.com/150','1', 'cliente');
insert into client values (2,'r77','{bcrypt}$2a$10$Du0cfJx6hVx32g30grRZve5kcocChzloRoTSpJhVOaG.296Ng6zFy','ruben','Szeker','ruben@gmail.com','micasa', 'phone','https://via.placeholder.com/150','1', 'cliente');
insert into client values(3,'roure','{bcrypt}$2a$10$Du0cfJx6hVx32g30grRZve5kcocChzloRoTSpJhVOaG.296Ng6zFy','Josep','Roure','roure@gmail.com','casa2','phone','https://via.placeholder.com/150','1','cliente');

--Seller
--(idSeller,username,password,firstname, lastname,email,image,Status,type);
insert into seller values(1,'lala','{bcrypt}$2a$10$Du0cfJx6hVx32g30grRZve5kcocChzloRoTSpJhVOaG.296Ng6zFy','Lala','Belda','lala@email.com','https://via.placeholder.com/150',1,'Seller');
insert into seller values (2,'acortes','{bcrypt}$2a$10$Du0cfJx6hVx32g30grRZve5kcocChzloRoTSpJhVOaG.296Ng6zFy','Anna','Belen','annabelen@gmail.com','https://via.placeholder.com/150',1,'Seller');

--Company
--(name,phonenumber,email,addresses,social networks,image,idseller)
insert into company values ('companyname', 68661692, 'email', 'addresses', 'socialnetworks','https://via.placeholder.com/150',1);
insert into company values ('NIKE' ,686169256, 'nike@gmail.com', 'la fabrica', '','https://via.placeholder.com/150',1);
insert into company values ('MediaMarkt' ,686169253, 'mm@gmail.com', 'Mataro', '','https://via.placeholder.com/150',2);

insert into category values ('ropa', 'https://via.placeholder.com/150');
insert into category values ('technology', 'https://via.placeholder.com/150');
insert into category values ('uncategorized', 'https://via.placeholder.com/150');

insert into promotion (idpromotion) values (0);

insert into card values ('card number','ownername','22-12-19',1);

insert into sale values (1,'card','22-12-19',130,1);

--Product
-- (IdProduct,name, description,price,stock,img,companyName,promotion,categoryName,reviewGrade, visibility, image)
insert into product values (1,'Airmax','super chulas las bambas', 80,100,'https://via.placeholder.com/150','NIKE',0,'ropa',9,1);
insert into product values (2,'Bicicleta','',300,5,'https://via.placeholder.com/150','MediaMarkt',0,'technology',7,1);
insert into product values (3,'Volante','',200,1,'https://via.placeholder.com/150','MediaMarkt',0,'technology',2,1);
insert into product values (4,'Iphone','',1000,1000,'https://via.placeholder.com/150','MediaMarkt',0,'technology',1,1);
insert into product values (5,'Ordenador','',650,40,'https://via.placeholder.com/150','MediaMarkt',0,'technology',6,1);
insert into product values (6,'Pantalla','',150,30,'https://via.placeholder.com/150','MediaMarkt',0,'technology',10,1);
insert into product values (7,'Rueda 17"','',30,4,'https://via.placeholder.com/150','MediaMarkt',0,'technology',4,1);
insert into product values (8,'Sudadera Gris','',25,10,'https://via.placeholder.com/150','NIKE',0,'ropa',3,1);
insert into product values (9,'Camisa','',15,23,'https://via.placeholder.com/150','NIKE',0,'ropa',7,1);
insert into product values (10,'Cinturon','',5,20,'https://via.placeholder.com/150','NIKE',0,'ropa',8,1);

INSERT INTO USERS (username, name, secondName, email, password, enabled, role) (
    SELECT username, name, secondName, email, password, case (status) when '1' then true else false end as enabled, 'CLIENT' as role
    FROM client
    union all
    SELECT username, name, secondName, email, password, case (status) when '1' then 1 else 0 end as enabled, 'SELLER' as role
    FROM seller
    UNION ALL
    SELECT username, name, '' as secondName, email, password, case (status) when '1' then 1 else 0 end as enabled, 'ADMIN' as role
    FROM admin
);

INSERT INTO authorities (username, role) (
    SELECT username, role
    FROM users
);
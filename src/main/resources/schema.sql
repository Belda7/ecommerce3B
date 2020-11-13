drop table if exists client;
CREATE TABLE client (
                        idclient     INTEGER NOT NULL,
                        username      VARCHAR2(4000) NOT NULL,
                        password      VARCHAR2(4000) NOT NULL,
                        name          VARCHAR2(4000) NOT NULL,
                        secondName    VARCHAR2(4000) NOT NULL,
                        email         VARCHAR2(4000) NOT NULL,
                        directions    VARCHAR2(4000),
                        phonenumber   VARCHAR2(4000),
                        image         VARCHAR2(4000),
                        status        CHAR(1),
                        tipo          VARCHAR2(4000)
);

ALTER TABLE client ADD CONSTRAINT client_pk PRIMARY KEY ( idclient );

drop table if exists seller;
CREATE TABLE seller (
                        idseller   INTEGER NOT NULL,
                        username   VARCHAR2(4000) NOT NULL,
                        password   VARCHAR2(4000),
                        name       VARCHAR2(4000),
                        secondName VARCHAR2(4000),
                        email      VARCHAR2(4000),
                        image        VARCHAR2(4000),
                        status     CHAR(1),
                        tipo       VARCHAR2(4000)

);

ALTER TABLE seller ADD CONSTRAINT seller_pk PRIMARY KEY ( idseller );

drop table if exists category;
CREATE TABLE category (
    categoryname VARCHAR2(4000) NOT NULL,
                         image              VARCHAR2(4000)
);

ALTER TABLE category ADD CONSTRAINT category_pk PRIMARY KEY ( categoryname );

drop table if exists card;
CREATE TABLE card (
                      cardnumber         VARCHAR2(4000) NOT NULL,
                      owner              VARCHAR2(4000) NOT NULL,
                      expiredate         DATE NOT NULL,
                      client_idclient   INTEGER
);

ALTER TABLE card ADD CONSTRAINT card_pk PRIMARY KEY ( cardnumber );

drop table if exists company;
CREATE TABLE company (
                         companyname       VARCHAR2(4000) NOT NULL,
                         phonenumber       VARCHAR(20),
                         email             VARCHAR2(4000),
                         addresses        VARCHAR2(4000),
                         socialnetworks    VARCHAR2(4000),
                         image               VARCHAR2(4000),
                         seller_idseller   INTEGER

);

ALTER TABLE company ADD CONSTRAINT company_pk PRIMARY KEY ( companyname );

drop table if exists promotion;
CREATE TABLE promotion (
                           idpromotion       INTEGER NOT NULL,
                           untildate         DATE,
                           fromdate          DATE,
                           percentage        INTEGER,
                           discountineuros   NUMBER,
                           active            CHAR(1)
);

ALTER TABLE promotion ADD CONSTRAINT promotion_pk PRIMARY KEY ( idpromotion );

drop table if exists review;
CREATE TABLE review (
                        idreview            INTEGER NOT NULL,
                        content             VARCHAR2(4000),
                        grade               NUMBER,
                        name                VARCHAR2(4000) NOT NULL,
                        client_email        VARCHAR2(4000),
                        product_idproduct   INTEGER,
                        client_idclient    INTEGER
);

ALTER TABLE review ADD CONSTRAINT review_pk PRIMARY KEY ( idreview );

drop table if exists product;
CREATE TABLE product (
                         idproduct               INTEGER NOT NULL,
                         name                    VARCHAR2(4000) NOT NULL,
                         description             VARCHAR2(4000) NOT NULL,
                         price                   NUMBER NOT NULL,
                         stock                   INTEGER,
                         image                     VARCHAR2(4000),
                         company_companyname     VARCHAR2(4000),
                         promotion_idpromotion   INTEGER,
                         category_categoryname   VARCHAR2(4000),
                         review_grade            NUMBER,
                         visibility              TINYINT
);

ALTER TABLE product ADD CONSTRAINT product_pk PRIMARY KEY ( idproduct );

drop table if exists sale;
CREATE TABLE sale (
                      idsale             INTEGER NOT NULL,
                      paymentmethod      VARCHAR2(4000),
                      SaleDate           Date,
                      totalprice         NUMBER,
                      client_idclient   INTEGER

);

ALTER TABLE sale ADD CONSTRAINT sale_pk PRIMARY KEY ( idsale );

drop table if exists saledetail;
CREATE TABLE saledetail (
                            product_idproduct   INTEGER NOT NULL,
                            quantity            INTEGER,
                            pruductPrice        NUMBER NOT NULL,
                            saledetail_id       NUMBER NOT NULL,
                            sale_idsale         INTEGER NOT NULL

);

ALTER TABLE saledetail ADD CONSTRAINT saledetail_pk PRIMARY KEY ( saledetail_id );

drop table if exists ticket;
CREATE TABLE ticket (
                        idticket           INTEGER NOT NULL,
                        status             VARCHAR2(4000) NOT NULL,
                        initialdate        DATE NOT NULL,
                        content            VARCHAR2(4000) NOT NULL,
                        email              VARCHAR2(4000) NOT NULL,
                        name1              VARCHAR2(4000) NOT NULL,
                        client_email       VARCHAR2(4000),
                        seller_idseller    INTEGER,
                        client_idclient   INTEGER
);

ALTER TABLE ticket ADD CONSTRAINT ticket_pk PRIMARY KEY ( idticket );

drop table if exists admin;
CREATE TABLE admin (
                      username   VARCHAR2(4000) NOT NULL,
                      password   VARCHAR2(4000),
                      name       VARCHAR2(4000),
                      email      VARCHAR2(4000),
                      status     CHAR(1),
                      tipo       VARCHAR2(4000),
                      idadmin   INTEGER NOT NULL,
                     image              VARCHAR2(4000)
);

ALTER TABLE admin ADD CONSTRAINT admin_pk PRIMARY KEY ( idadmin );

ALTER TABLE card
    ADD CONSTRAINT card_client_fk FOREIGN KEY ( client_idclient )
        REFERENCES client ( idclient );

ALTER TABLE company
    ADD CONSTRAINT company_seller_fk FOREIGN KEY ( seller_idseller )
        REFERENCES seller ( idseller );

ALTER TABLE product
    ADD CONSTRAINT product_category_fk FOREIGN KEY ( category_categoryname )
        REFERENCES category ( categoryname );

ALTER TABLE product
    ADD CONSTRAINT product_company_fk FOREIGN KEY ( company_companyname )
        REFERENCES company ( companyname );

ALTER TABLE product
    ADD CONSTRAINT product_promotion_fk FOREIGN KEY ( promotion_idpromotion )
        REFERENCES promotion ( idpromotion );

ALTER TABLE review
    ADD CONSTRAINT review_client_fk FOREIGN KEY ( client_idclient )
        REFERENCES client ( idclient );

ALTER TABLE review
    ADD CONSTRAINT review_product_fk FOREIGN KEY ( product_idproduct )
        REFERENCES product ( idproduct );

ALTER TABLE sale
    ADD CONSTRAINT sale_client_fk FOREIGN KEY ( client_idclient )
        REFERENCES client ( idclient );

ALTER TABLE saledetail
    ADD CONSTRAINT saledetail_product_fk FOREIGN KEY ( product_idproduct )
        REFERENCES product ( idproduct );

ALTER TABLE saledetail
    ADD CONSTRAINT saledetail_sale_fk FOREIGN KEY ( sale_idsale )
        REFERENCES sale ( idsale );

ALTER TABLE ticket
    ADD CONSTRAINT ticket_client_fk FOREIGN KEY ( client_idclient )
        REFERENCES client ( idclient );

ALTER TABLE ticket
    ADD CONSTRAINT ticket_seller_fk FOREIGN KEY ( seller_idseller )
        REFERENCES seller ( idseller );

DROP TABLE if EXISTS users;
CREATE TABLE users
(
    username VARCHAR (100) PRIMARY KEY,
    name VARCHAR (255),
    secondName VARCHAR (255),
    email VARCHAR (300),
    password VARCHAR(70) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    role VARCHAR (100)
);

DROP TABLE if EXISTS authorities;
CREATE TABLE authorities (
     authority_id int(11) NOT NULL AUTO_INCREMENT,
     username varchar(45) NOT NULL,
     role varchar(45) NOT NULL,
     PRIMARY KEY (authority_id),
     UNIQUE (role,username),
     CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
                         );
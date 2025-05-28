drop table if exists atributos_producto;
drop table if exists producto_categoria;
drop table if exists productos_relacionados;
drop table if exists productos;
drop table if exists categorias;
drop table if exists relaciones;

drop sequence if exists categorias_seq;
drop sequence if exists productos_seq;
drop sequence if exists relaciones_seq;

    create sequence categorias_seq start with 1 increment by 50;

    create sequence productos_seq start with 1 increment by 50;

    create sequence relaciones_seq start with 1 increment by 50;

    create table atributos_producto (
        id_producto bigint not null,
        nombre varchar(255) not null,
        valor varchar(255) not null
    );

    create table categorias (
        id bigint not null,
        nombre varchar(255) not null,
        primary key (id)
    );

    create table producto_categoria (
        categoria_id bigint not null,
        producto_id bigint not null,
        primary key (categoria_id, producto_id)
    );

    create table productos (
        creado timestamp(6) with time zone not null,
        id bigint not null,
        id_cuenta bigint not null,
        modificado timestamp(6) with time zone,
        gtin varchar(255) not null unique,
        miniatura varchar(255),
        nombre varchar(255) not null,
        sku varchar(255) not null unique,
        texto_corto varchar(255),
        primary key (id)
    );

    create table productos_relacionados (
        id_producto_destino bigint not null,
        id_producto_origen bigint not null,
        relacion_fk bigint unique,
        primary key (id_producto_destino, id_producto_origen)
    );

    create table relaciones (
        id bigint not null,
        description varchar(255),
        nombre varchar(255) not null,
        primary key (id)
    );

    alter table if exists atributos_producto 
       add constraint FK99yjqg0ouc5wg2x6rpunqm5ac 
       foreign key (id_producto) 
       references productos;

    alter table if exists producto_categoria 
       add constraint FKck76h1dqwbw3rp8gkxkxytqe6 
       foreign key (categoria_id) 
       references categorias;

    alter table if exists producto_categoria 
       add constraint FKfahqc7k27mgnlrr5q6oylure7 
       foreign key (producto_id) 
       references productos;

    alter table if exists productos_relacionados 
       add constraint FKt8d2estsslj1o8cla4nmcl85a 
       foreign key (id_producto_destino) 
       references productos;

    alter table if exists productos_relacionados 
       add constraint FKgef9sn2qvx3aqmjqwlw2wdm0c 
       foreign key (id_producto_origen) 
       references productos;

    alter table if exists productos_relacionados 
       add constraint FK1h6fk89lu2crxqetr9d5474ke 
       foreign key (relacion_fk) 
       references relaciones;
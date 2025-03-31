
    create sequence categorias_seq start with 1 increment by 50;

    create sequence productos_seq start with 1 increment by 50;

    create sequence relaciones_seq start with 1 increment by 50;

    create table categorias (
        id bigint not null,
        nombre varchar(255),
        primary key (id)
    );

    create table producto_categoria (
        categoria_id bigint not null,
        producto_id bigint not null,
        primary key (categoria_id, producto_id)
    );

    create table productos (
        creado timestamp(6),
        id bigint not null,
        modificado timestamp(6),
        gtin varchar(255),
        miniatura varchar(255),
        nombre varchar(255),
        sku varchar(255),
        texto_corto varchar(255),
        primary key (id)
    );

    create table relaciones (
        id bigint not null,
        description varchar(255),
        nombre varchar(255),
        primary key (id)
    );

    alter table if exists producto_categoria 
       add constraint FKck76h1dqwbw3rp8gkxkxytqe6 
       foreign key (categoria_id) 
       references categorias;

    alter table if exists producto_categoria 
       add constraint FKfahqc7k27mgnlrr5q6oylure7 
       foreign key (producto_id) 
       references productos;

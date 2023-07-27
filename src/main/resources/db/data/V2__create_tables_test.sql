CREATE TABLE Categories(
        id UUID PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE,
        description VARCHAR(1000));

CREATE TABLE Customers(
        id UUID PRIMARY KEY,
        first_name VARCHAR(100),
        last_name VARCHAR(100),
        email VARCHAR(100));

CREATE TABLE Orders(
        id UUID PRIMARY KEY,
        created_on DATE,
        customer UUID,
        country varchar(255),
        city varchar(255),
        details varchar(255),
        FOREIGN KEY(customer) REFERENCES Customers(id));

CREATE TABLE Suppliers(
        id UUID PRIMARY KEY,
        name VARCHAR(100));

CREATE TABLE Products(
        id UUID PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE,
        description VARCHAR(1000),
        price FLOAT NOT NULL,
        weight FLOAT NOT NULL,
        category_id UUID,
        supplier_id UUID,
        FOREIGN KEY(category_id) REFERENCES Categories(id),
        FOREIGN KEY(supplier_id) REFERENCES Suppliers(id));

CREATE TABLE Order_Details(
        id UUID PRIMARY KEY,
        order_id UUID,
        product_id UUID,
        quantity INTEGER,
        FOREIGN KEY(order_id) REFERENCES Orders(id),
        FOREIGN KEY (product_id) REFERENCES Products(id)
);


CREATE TABLE Addresses(
       id UUID PRIMARY KEY,
       country VARCHAR(100),
       city VARCHAR(100),
       details VARCHAR(100));

CREATE TABLE Locations(
        id UUID PRIMARY KEY,
        name VARCHAR(100),
        country VARCHAR(100),
        city VARCHAR(100),
        details VARCHAR(100)
        );

CREATE TABLE Stocks(
       id UUID PRIMARY KEY,
       product_id UUID,
       quantity INT,
       location_id UUID,
       FOREIGN KEY(product_id) REFERENCES Products(id),
       FOREIGN KEY(location_id) REFERENCES Locations(id));

# RFID Book Library

RFID Book library application written on Spring with MySQL database.


![Logo](https://png.pngtree.com/png-vector/20190411/ourlarge/pngtree-vector-book-icon-png-image_925656.jpg)


## Features

- User login, logout, register.
- User borrow books on Web or by RFID.
- Administrator add/delete book, update book information.
- Administrator add/delete book, update user information.
- Administrator manager book by RFID.
- Administrator manager borrow information.
- Statistics borrow on Web or RFID, Top Book borrow, Top Category borrow.


## Installation

Install mysql

```bash
  Create database name "books"
  import book.sql
```

Install my-project with mvn

```bash
  mvn clean install
  mvn spring-boot:run
```

## Account
- Admin account:
    - Username: admin
    - Password: 123
- Member account:
    - Username: Test
    - Password: 123
## Screenshots
User
![Home](https://cdn.discordapp.com/attachments/900622174530969640/974964037139980298/unknown.png)

Admin
![Dashboard1](https://media.discordapp.net/attachments/900622174530969640/974964249598251068/unknown.png?width=1246&height=620)
![Dashboard2](https://cdn.discordapp.com/attachments/900622174530969640/974964455240790036/unknown.png)

DROP TABLE if EXISTS t_user;
CREATE TABLE t_user
(
    id    int AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(50) NOT null,
    age   INT(8)      NOT null,
    phone char(11)    null comment '号码'
);
DROP DATABASE if exists InternNET;
CREATE DATABASE InternNET;
USE InternNET;

CREATE TABLE users (
  userID int PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
  username varchar(255) BINARY NOT NULL UNIQUE,
  password varchar(255) BINARY NOT NULL
);

CREATE TABLE profile (
  profileID int PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
  userID int(11) NOT NULL UNIQUE,
  imageLink varchar(255),
  firstName varchar(255),
  lastName varchar(255),
  location varchar(255),
  FOREIGN KEY (userID) REFERENCES users(userID)
);
  
CREATE TABLE internship (
  internshipID int PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
  userID int(11) NOT NULL,
  company varchar(255) NOT NULL,
  position varchar(255) NOT NULL,
  location varchar(255) NOT NULL,
  appStatus varchar(255) NOT NULL,
  appDeadline date NOT NULL,
  dateApplied date NOT NULL,
  timeStamp timestamp NOT NULL,
  FOREIGN KEY (userID) REFERENCES users(userID)
);

CREATE TABLE friends (
  friendID int PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
  friend1 int(11) NOT NULL,
  friend2 int(11) NOT NULL,
  timeStamp timestamp NOT NULL,
  FOREIGN KEY (friend1) REFERENCES users(userID),
  FOREIGN KEY (friend2) REFERENCES users(userID)
);

CREATE TABLE notification (
  notificationID int PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
  sendingUserID int(11) NOT NULL,
  receivingUserID int(11) NOT NULL,
  type int(11) NOT NULL,
  imageLink varchar(255) NOT NULL,
  timeStamp timestamp NOT NULL,
  FOREIGN KEY (sendingUserID) REFERENCES users(userID),
  FOREIGN KEY (receivingUserID) REFERENCES users(userID)
);

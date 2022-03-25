CREATE TABLE Course (
                        Course_Id   INTEGER      NOT NULL AUTO_INCREMENT,
                        Course_Name VARCHAR(128) NOT NULL,
                        PRIMARY KEY (Course_Id)
);

CREATE TABLE Student (
                         Student_Id   INTEGER      NOT NULL AUTO_INCREMENT,
                         First_Name VARCHAR(128) NOT NULL,
                         Last_Name VARCHAR(128) NOT NULL,
                         PRIMARY KEY (Student_Id)
);
//Table groupcontact
CREATE TABLE groupcontact(
cid MEDIUMINT,
gid MEDIUMINT,
PRIMARY KEY(cid, gid),
FOREIGN KEY (cid) REFERENCES contact(id),
FOREIGN KEY (gid) REFERENCES phonegroup(id));

//Table contact
CREATE TABLE contact(
id MEDIUMINT NOT NULL AUTO_INCREMENT,
firstname VARCHAR(30),
lastname VARCHAR(30),
homephone VARCHAR(30),
mobile VARCHAR(30),
PRIMARY KEY(id));



//Table phonegroup
CREATE TABLE phonegroup(
id MEDIUMINT NOT NULL AUTO_INCREMENT,
name VARCHAR(30),
PRIMARY KEY(id);
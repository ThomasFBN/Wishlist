CREATE TABLE WISHLIST (
                          LISTID INTEGER AUTO_INCREMENT,
                          WISHLISTNAME VARCHAR(30),
                          PRIMARY KEY (LISTID)
);

CREATE TABLE WISH (
                      WISHID INTEGER AUTO_INCREMENT,
                      NAME VARCHAR(30),
                      ITEMURL VARCHAR(255),
                      PRICE DOUBLE (10, 2),
                      LISTID INTEGER,
                      PRIMARY KEY (WISHID),
                      FOREIGN KEY (LISTID) REFERENCES WISHLIST(LISTID)
);

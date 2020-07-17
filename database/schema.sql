BEGIN TRANSACTION;

DROP TABLE IF EXISTS transfers;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS transfer_types;
DROP TABLE IF EXISTS transfer_statuses;

DROP SEQUENCE IF EXISTS seq_transfer_type_id;
DROP SEQUENCE IF EXISTS seq_transfer_status_id;
DROP SEQUENCE IF EXISTS seq_user_id;
DROP SEQUENCE IF EXISTS seq_account_id;
DROP SEQUENCE IF EXISTS seq_transfer_id;

CREATE SEQUENCE seq_transfer_type_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE SEQUENCE seq_transfer_status_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE transfer_types (
	transfer_type_id int DEFAULT nextval('seq_transfer_type_id'::regclass) NOT NULL,
	transfer_type_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_types PRIMARY KEY (transfer_type_id)
);

CREATE TABLE transfer_statuses (
	transfer_status_id int DEFAULT nextval('seq_transfer_status_id'::regclass) NOT NULL,
	transfer_status_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_statuses PRIMARY KEY (transfer_status_id)
);

CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE accounts (
	account_id int DEFAULT nextval('seq_account_id'::regclass) NOT NULL,
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_accounts PRIMARY KEY (account_id),
	CONSTRAINT FK_accounts_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE transfers (
	transfer_id int DEFAULT nextval('seq_transfer_id'::regclass) NOT NULL,
	transfer_type_id int NOT NULL,
	transfer_status_id int NOT NULL,
	account_from int NOT NULL,
	account_to int NOT NULL,
	amount decimal(13, 2) NOT NULL,
	CONSTRAINT PK_transfers PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfers_accounts_from FOREIGN KEY (account_from) REFERENCES accounts (account_id),
	CONSTRAINT FK_transfers_accounts_to FOREIGN KEY (account_to) REFERENCES accounts (account_id),
	CONSTRAINT FK_transfers_transfer_statuses FOREIGN KEY (transfer_status_id) REFERENCES transfer_statuses (transfer_status_id),
	CONSTRAINT FK_transfers_transfer_types FOREIGN KEY (transfer_type_id) REFERENCES transfer_types (transfer_type_id),
	CONSTRAINT CK_transfers_not_same_account CHECK  ((account_from<>account_to)),
	CONSTRAINT CK_transfers_amount_gt_0 CHECK ((amount>0))
);


INSERT INTO transfer_statuses (transfer_status_desc) VALUES ('Pending');
INSERT INTO transfer_statuses (transfer_status_desc) VALUES ('Approved');
INSERT INTO transfer_statuses (transfer_status_desc) VALUES ('Rejected');

INSERT INTO transfer_types (transfer_type_desc) VALUES ('Request');
INSERT INTO transfer_types (transfer_type_desc) VALUES ('Send');

INSERT INTO users (username,password_hash) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC');
INSERT INTO users (username,password_hash) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC');

INSERT INTO accounts (user_id,balance) VALUES (1,1000);
INSERT INTO accounts (user_id,balance) VALUES (2,1000);


COMMIT TRANSACTION;

Select accounts.user_id, accounts.balance, accounts.account_id from accounts join users on accounts.user_id = users.user_id where users.username = 'test';

Select username, user_id  from users;

Update accounts set balance = balance-? where user_id =?;
Update accounts set balance = balance+100 where user_id =3;

select transfers.transfer_id, users.username, transfers.account_to, transfers.amount
from transfers
JOIN accounts ON transfers.account_to = accounts.account_id
JOIN users ON accounts.user_id = users.user_id
where transfers.account_from = '3';

Select transfers.transfer_id, transfers.account_from, u.username, users.username, transfer_types.transfer_type_desc, transfer_statuses.transfer_status_desc, transfers.amount
from transfers
JOIN accounts ON transfers.account_to = accounts.account_id
JOIN users ON accounts.user_id = users.user_id
Join transfer_types on transfers.transfer_type_id = transfer_types.transfer_type_id
Join transfer_statuses on transfers.transfer_status_id = transfer_statuses.transfer_status_id
join users as u on transfers.account_from = u.user_id
where transfers.transfer_id =10;


select transfers.transfer_id, u.username as from, users.username as to, transfers.amount
			from transfers
				JOIN accounts ON transfers.account_to = accounts.account_id
				JOIN users ON accounts.user_id = users.user_id
				join users as u on transfers.account_from = u.user_id
				where transfers.account_from = 3
				OR transfers.account_to = 3;

SELECT transfer_id, 'from' AS from_or_to, username, amount
FROM transfers
JOIN accounts ON transfers.account_from = accounts.account_id
JOIN users ON accounts.user_id = users.user_id
WHERE account_to = 1
AND transfer_type_id = 2
UNION ALL
SELECT transfer_id, 'to' AS from_or_to, username, amount
FROM transfers
JOIN accounts ON transfers.account_to = accounts.account_id
JOIN users ON accounts.user_id = users.user_id
WHERE  account_from = 1
and transfer_type_id = 2;


Select transfer.transfer_id, 

select transfers.transfer_id, users.username AS username, transfers.amount from transfers JOIN accounts ON transfers.account_to = accounts.account_id JOIN users ON accounts.user_id = users.user_id where transfers.account_from = (SELECT accounts.account_id FROM accounts JOIN users on accounts.user_id = users.user_id WHERE users.username = 'user');
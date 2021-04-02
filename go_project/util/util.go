package util

import (
	"database/sql"
	"log"
	"os"

	"github.com/joho/godotenv"
	_ "github.com/lib/pq"
)

func InitDB() {
	CreateUserTable()
	CreateFriendManageTable()
}

func CreateUserTable() {
	db := CreatConnection()
	defer db.Close()
	const q = `
		CREATE TABLE IF NOT EXISTS users (
		id serial PRIMARY KEY,
		email_address varchar(256) NOT NULL UNIQUE,
		created_date timestamp with time zone DEFAULT current_timestamp
	)`
	_, err := db.Query(q)
	CheckError(err)
}

func CreateFriendManageTable() {
	db := CreatConnection()
	defer db.Close()
	const q = `
		CREATE TABLE  IF NOT EXISTS friendmanagement(
			id INT GENERATED ALWAYS AS IDENTITY ,
			user_fk_1 INT NOT NULL, 
			user_fk_2 INT NOT NULL CHECK (user_fk_2 > user_fk_1),	
			CONSTRAINT fk_users1
			   FOREIGN KEY(user_fk_1)	  
			   REFERENCES users(id)
			   ON DELETE CASCADE, 
			 CONSTRAINT fk_users2
			   FOREIGN KEY(user_fk_2)	  
			   REFERENCES users(id)
			   ON DELETE CASCADE	,
		    PRIMARY KEY (user_fk_1,user_fk_2)
		 )`
	_, err := db.Query(q)
	CheckError(err)
}

func CreatConnection() *sql.DB {
	err := godotenv.Load(".env")
	if err != nil {
		log.Fatalf("Error loading .env file")
	}
	db, err := sql.Open("postgres", os.Getenv("POSTGRES_PROPERTIES"))
	CheckError(err)
	err = db.Ping()
	CheckError(err)
	return db
}

func CheckError(err error) {
	if err != nil {
		panic(err)
	}
}

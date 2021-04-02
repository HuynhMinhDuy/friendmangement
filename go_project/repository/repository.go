package repository

import (
	"go_project/models"
	"go_project/util"
	"log"
)

func CreateUser(email string) error {
	db := util.CreatConnection()
	defer db.Close()
	const qry = `INSERT INTO "users" ("email_address") VALUES ($1)`
	_, err := db.Exec(qry, email)
	return err
}

func GetAllUser() []models.User {
	var listUser []models.User
	db := util.CreatConnection()
	const qry = `SELECT * FROM users`
	rows, err := db.Query(qry)
	defer rows.Close()
	defer db.Close()
	for rows.Next() {
		var user models.User
		err = rows.Scan(&user.ID, &user.Email, &user.Created_Date)
		listUser = append(listUser, user)
	}
	util.CheckError(err)
	return listUser
}

func CreateConnection(email1, email2 string) error {

	userId1, err1 := FindByEmail(email1)

	if err1 != nil {
		return err1
	}

	userId2, err2 := FindByEmail(email2)

	if err2 != nil {
		return err2
	}

	var err error
	db := util.CreatConnection()
	defer db.Close()
	const qry = `INSERT INTO friendmanagement("user_fk_1","user_fk_2") VALUES ($1,$2)`
	if userId1 < userId2 {
		_, err = db.Exec(qry, userId1, userId2)
	} else {
		_, err = db.Exec(qry, userId2, userId1)
	}

	return err

}

func GetFriendList(email string) ([]string, error) {

	var listEmail []string
	id, err := FindByEmail(email)

	if err == nil {
		db := util.CreatConnection()
		const qry = `SELECT email_address FROM users WHERE id IN (
						SELECT user_fk_2  FROM friendmanagement WHERE user_fk_1=$1
						UNION
						SELECT user_fk_1  FROM friendmanagement WHERE user_fk_2=$1
					)`

		rows, _ := db.Query(qry, id)
		defer db.Close()
		for rows.Next() {
			var email string
			err = rows.Scan(&email)
			listEmail = append(listEmail, email)
		}
	}
	return listEmail, err
}

func FindCommonFriends(email1, email2 string) ([]string, error) {

	var listCommon []string

	listFriend1, err1 := GetFriendList(email1)

	if err1 != nil {
		return listCommon, err1
	}

	listFriend2, err2 := GetFriendList(email2)

	if err2 != nil {
		return listCommon, err2
	}

	for _, friend1 := range listFriend1 {
		for _, friend2 := range listFriend2 {
			if friend1 == friend2 {
				listCommon = append(listCommon, friend1)
			}
		}
	}

	return listCommon, nil

}

func FindByEmail(email string) (int, error) {
	var id int
	db := util.CreatConnection()
	defer db.Close()
	const qry = `SELECT id FROM users WHERE email_address=$1`
	err := db.QueryRow(qry, email).Scan(&id)
	if err != nil {
		log.Println("Cannot find this email", email)
	}
	return id, err
}

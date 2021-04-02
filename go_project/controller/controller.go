package controller

import (
	"encoding/json"
	"go_project/models"
	"go_project/repository"
	"log"
	"net/http"
)

func CreateUser(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var newUser models.User
	var success = &models.SuccessResponse{}
	if err := json.NewDecoder(req.Body).Decode(&newUser); err != nil {
		log.Printf("Invalid Json Format")
		json.NewEncoder(w).Encode(success)
		return
	}
	err := repository.CreateUser(newUser.Email)
	if err == nil {
		success.Success = true
	}
	json.NewEncoder(w).Encode(success)
}

func GetAllUser(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var listUser []models.User = repository.GetAllUser()
	json.NewEncoder(w).Encode(listUser)
}

func CreateConnection(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var friends models.FriendRequest
	var success models.SuccessResponse
	if err := json.NewDecoder(req.Body).Decode(&friends); err != nil {
		log.Printf("Invalid Json Format")
		json.NewEncoder(w).Encode(success)
		return
	}
	err := repository.CreateConnection(friends.Friends[0], friends.Friends[1])
	if err == nil {
		success.Success = true
	}
	json.NewEncoder(w).Encode(success)
}

func GetFriendList(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var email models.EmailRequest
	var friendList models.FriendListReponse
	var success models.SuccessResponse

	if err := json.NewDecoder(req.Body).Decode(&email); err != nil {
		log.Printf("Invalid Json Format")
		json.NewEncoder(w).Encode(success)
		return
	}

	listEmail, err := repository.GetFriendList(email.Email)

	if err == nil {
		friendList.Count = len(listEmail)
		friendList.Friends = listEmail
		friendList.Success = true
	}

	json.NewEncoder(w).Encode(friendList)

}

func GetCommonFriends(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var friends models.FriendRequest
	var friendCommonList models.FriendListReponse
	var success models.SuccessResponse

	if err := json.NewDecoder(req.Body).Decode(&friends); err != nil {
		json.NewEncoder(w).Encode(success)
		return
	}

	listCommon, err := repository.FindCommonFriends(friends.Friends[0], friends.Friends[1])

	if err == nil {
		friendCommonList.Count = len(listCommon)
		friendCommonList.Friends = listCommon
		friendCommonList.Success = true
	}
	json.NewEncoder(w).Encode(friendCommonList)
}

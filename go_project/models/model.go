package models

type User struct {
	ID           uint64 `json:"id"`
	Email        string `json:"email"`
	Created_Date string `json:"created_date"`
}

type SuccessResponse struct {
	Success bool `json:"success"`
}

type FriendRequest struct {
	Friends []string `json:"friends"`
}

type EmailRequest struct {
	Email string `json:"email"`
}

type FriendListReponse struct {
	Success bool     `json:"success"`
	Friends []string `json:"friends"`
	Count   int      `json:"count"`
}

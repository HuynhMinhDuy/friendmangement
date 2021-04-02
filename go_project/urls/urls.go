package urls

import (
	"go_project/controller"

	"github.com/gorilla/mux"
)

func Router() *mux.Router {

	router := mux.NewRouter()

	router.HandleFunc("/api/v1/register", controller.CreateUser).Methods("POST")
	router.HandleFunc("/api/v1/users", controller.GetAllUser).Methods("GET")

	router.HandleFunc("/api/v1/connect", controller.CreateConnection).Methods("POST")
	router.HandleFunc("/api/v1/list", controller.GetFriendList).Methods("POST")
	router.HandleFunc("/api/v1/common", controller.GetCommonFriends).Methods("POST")

	return router
}

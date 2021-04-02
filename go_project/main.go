package main

import (
	"go_project/urls"
	"go_project/util"
	"log"
	"net/http"
)

func main() {
	util.InitDB()
	router := urls.Router()
	log.Fatal(http.ListenAndServe(":8080", router))
}

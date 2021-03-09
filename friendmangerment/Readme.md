# S3_FriendsManagementAPI_DuyHuynh


# Installation

This projects requires Maven to run

Install the dependencies and start the server

```sh
$ mvn clean install
$ mvn spring-boot: run
```

# APIs Documentation

## Add User API

```sh
Request Method: POST /api/v1/register

Body :
{
    "email" : "test@gmail.com"
}
```
> Response Payload

```sh
{
    "success": true    
}
```

## Create Friend conenction API

```sh
Request Method: POST /api/v1/connect

Body :
{ 
"friends":
    [
        "test1@gmail.com",
        "test2@gmail.com"
    ]	 
}
```
> Response Payload

```sh
{
    "success": true
}
```

## Get Friend List Of An User API

```sh
Request Method: POST /api/v1/list
Body:
	{ 
    		"email" : "test5@gmail.com"
	}
```

>Response Payload
```sh
{   
        "friends": [
            "test2@gmail.com"
        ],
        "count": 1,   
        "success": true
}
```

## Get Common Friends API

```sh
Request Method: POST /api/v1/common

Body
{
    "friends":
    [
        "test2@gmail.com", 
        "test5@gmail.com"
    ]
}
```
> Response Payload

```sh
{    
        "friends": [
            "test1@gmail.com"
        ],
        "count": 1,   
        "success": true
}
```

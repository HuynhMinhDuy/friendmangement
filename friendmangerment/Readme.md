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

> Request Method: `POST` `/api/v1/register`
> Body :
{
"email" : "test@gmail.com"
}

> Data

| email_address |
| --------------|
|  test@gmail.com.  |

> Sample Request

```sh
curl --location --request POST 'http://localhost:8081/api/v1/users' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'email=test2@gmail.com'
```
> Response Payload

```sh
{
    “Status”: true    
}
```

## Create Friend conenction API

> Request Method: `POST` `/api/v1/connect `
> Body
{ "friends":
[
"test1@gmail.com",
"test2@gmail.com"
]	 
}


> Sample Request

```sh
Request Method. POST 'http://localhost:8080/api/v1/connect 

Body : 

    "friends":
    [
      "test2@gmail.com", "test5@gmail.com"
    ]   
  }'
```

> Response Payload

```sh
{
    "success": true
}
```

## Get Friend List Of An User API

> Request Method: `POST` `/api/v1/list`
> Body

	{ 
    		"email" : "test5@gmail.com"
	}



> Sample Request

```sh
curl --location --request GET 'http://localhost:8080/api/v1/list```

> Response Payload

```sh
{
    "data": {
        "friends": [
            "test2@gmail.com"
        ],
        "count": 1
    },
    "success": true
}
```

## Get Common Friends API

> Request Method: `GET` `/api/v1/friendships/common`

> Data

```sh
{
    "friends":
    [
        "test2@gmail.com", "test5@gmail.com"
    ]
}
```

> Sample Request

```sh
curl --location --request GET 'http://localhost:8080/api/v1/friendships/common' \
--header 'Content-Type: application/json' \
--data-raw '{
    "friends":
    [
        "test2@gmail.com", "test5@gmail.com"
    ]
}'
```

> Response Payload

```sh
{
    "data": {
        "friends": [
            "test1@gmail.com"
        ],
        "count": 1
    },
    "success": true
}
```

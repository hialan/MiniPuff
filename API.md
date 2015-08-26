# MiniPuff API

## GET /users/recommand_stream

Get main question stream

### Request:

| parameter | type | required | description |
|-----------|------|----------|-------------|
| start     | int  | optional | for pagination, start from which object. Default: 1 |
| count     | int  | optional | for pagination, count of return object. Default: 10 |

### Response:

````json
{
    "users": [
        {
            "id": "facebook id 1",
            "nick_name": "red puff",
            "profile_image": "http://some.url/some_image.jpg"
        },
        {
            "id": "facebook id 2",
            "nick_name": "blue puff",
            "profile_image": "http://some.url/some_image.jpg"
        },
        {
            "id": "facebook id 3",
            "nick_name": "green puff",
            "profile_image": "http://some.url/some_image.jpg"
        },
        {
            "id": "facebook id 4",
            "nick_name": "yellow puff",
            "profile_image": "http://some.url/some_image.jpg"
        }
    ]
}
````

## POST /users/:id/answers

answer :id's questions 


## GET /users/:id/profile

Get user profile (with questions)

### Request:

no parameter

### Response:

````json
{
    "id": "<facebook id>",
    "profile_image": "http://some.url/some_image.jpg",
    "questions": [
        {
            "id": 1,
            "description": "question a",
            "options": [
                {
                    "id": 1,
                    "text": "option a-1"
                },
                {
                    "id": 2,
                    "text": "option a-2"
                }
            ]
        },
        {
            "id": 2,
            "description": "question b",
            "options": [
                {
                    "id": 1,
                    "text": "option b-1"
                },
                {
                    "id": 2,
                    "text": "option b-2"
                }
            ]
        },
        {
            "id": 3,
            "description": "question c",
            "options": [
                {
                    "id": 1,
                    "text": "option c-1"
                },
                {
                    "id": 2,
                    "text": "option c-2"
                }
            ]
        }
    ]
}
````


## POST /users/:id/profile

Update user profile (a profile picture and 3 multi-select questions)

### Request

### Response

## GET /account/match_users

get user list who knows all the right answers to my questions

### Request

no parameter

### Response

````json
{
    "users": [
        {
            "id": "facebook id 1",
            "nick_name": "red puff",
            "profile_image": "http://some.url/some_image.jpg"
        },
        {
            "id": "facebook id 2",
            "nick_name": "blue puff",
            "profile_image": "http://some.url/some_image.jpg"
        },
        {
            "id": "facebook id 3",
            "nick_name": "green puff",
            "profile_image": "http://some.url/some_image.jpg"
        },
        {
            "id": "facebook id 4",
            "nick_name": "yellow puff",
            "profile_image": "http://some.url/some_image.jpg"
        }
    ]
}
````


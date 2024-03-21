# Request Parts

RequestLine:
- Method
- Path
- Query

Headers

Body

## Method

- GET - read
- POST - create
- PUT - update
- PATCH - partial update
- DELETE - remove
- BLOCK...

## Path

https://api.vk.com/method/status.get

https://api.vk.com/method/wall.getById/{id} -> https://api.vk.com/method/wall.getById/1

## Query

https://api.vk.com?method=wall.getById&id={id} https://api.vk.com?method=wall.getById&id=1

## Headers

* X-Custom <-
* Custom
* SOAP 1.1 (method -> SOAPAction, body -> params, POST)

## Body

* Request --> {"jsonrpc": "2.0", "method": "subtract", "params": \[42, 23], "id": 1}
* Response <-- {"jsonrpc": "2.0", "result": 19, "id": 1}

## Combination

### REST

- GET - read
- POST - create
- PUT - update
- PATCH - partial update
- DELETE - remove

* 2xx - OK
* 4xx - Client Error
* 5xx - Server Error

GitHub:

* GET /users - list of users
* GET /users/{userId} - get concrete user
* POST /users - create new user in collection (list.add(new User()))
* PUT /users/{userId} - update concrete user
* DELETE /users - delete concrete user
* DELETE /users/{userId} - delete concrete user

* GET /users/{userId}/repos - list of concrete user repos
* GET /users/{userId}/repos/{repoId} - concrete user concrete repo
* GET /users/{userId}/repos/{repoId}/issues
* GET /users/{userId}/repos/{repoId}/issues/{issueId}
* GET /users/{userId}/repos/{repoId}/issues/{issueId}/messages
* GET /users/{userId}/repos/{repoId}/issues/{issueId}/messages/{messageId}
* GET /users/{userId}/repos/{repoId}/issues/{issueId}/messages/{messageId}/reactions
* GET /users/{userId}/repos/{repoId}/issues/{issueId}/messages/{messageId}/reactions/{reactionId}

examples: GitHub REST API, Stripe API

## Tasks

- Path:
  - /crud.getAll -> Api.getAll (VK like)
  - /crud.getById?id=1 -> Api.getById(1)
- REST:
  - GET /items
  - GET /items/{id} <- 



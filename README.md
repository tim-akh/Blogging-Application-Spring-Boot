# Blog application REST API

## Project description

This is a REST API for a blogging application providing you with such opportunities as creating your own publications, leaving comments, upvoting and downvoting content. The application supports basic authentication.

## Roles

**USER**: Basic functional + Reporting inappropriate content <br />
**ADMIN**: Basic functional + Banning users + Deleting inappropriate content

## Endpoints

#### Authentication and Registration Service

|HTTP Method|URL|Description|
|---|---|---|
|`POST`|http://localhost:8000/api/v1/auth/signin | Sign In With Username and Password |
|`POST`|http://localhost:8000/api/v1/auth/signup | Sign Up With Username, Email, and Password |

#### User Service

|HTTP Method|URL|Description|
|---|---|---|
|`GET`|http://localhost:8000/api/v1/users/ | Get All Users [Admin] |
|`POST`|http://localhost:8000/api/v1/users | Create new User |
|`PUT`|http://localhost:8000/api/v1/users/{id} | Update User by ID |
|`GET`|http://localhost:8000/api/v1/users/{username} | Get User by Username |
|`DELETE`|http://localhost:8000/api/v1/users/{id} | Delete User by ID |

#### Publication Service

|HTTP Method|URL|Description|
|---|---|---|
|`GET`|http://localhost:8000/api/v1/publications/ | Get All Publications |
|`POST`|http://localhost:8000/api/v1/publications | Create new Publication |
|`PUT`|http://localhost:8000/api/v1/publications/{id} | Update Publication by ID |
|`GET`|http://localhost:8000/api/v1/publications/{id} | Get Publication by ID |
|`DELETE`|http://localhost:8000/api/v1/publications/{id} | Delete Publication by ID |

#### Comment Service

|HTTP Method|URL|Description|
|---|---|---|
|`POST`|http://localhost:8000/api/v1/comments | Create new Comment |
|`PUT`|http://localhost:8000/api/v1/comments/{id} | Update Comment by ID |
|`GET`|http://localhost:8000/api/v1/comments/{id} | Get Comment by ID [Admin] |
|`DELETE`|http://localhost:8000/api/v1/comments/{id} | Delete Comment by ID |

#### Vote Service

|HTTP Method|URL|Description|
|---|---|---|
|`POST`|http://localhost:8000/api/v1/votes | Create new Vote |
|`PUT`|http://localhost:8000/api/v1/votes/{id} | Update Vote by ID |
|`DELETE`|http://localhost:8000/api/v1/votes/{id} | Delete Vote by ID |

#### Report Service

|HTTP Method|URL|Description|
|---|---|---|
|`GET`|http://localhost:8000/api/v1/reports/ | Get All Reports [Admin] |
|`POST`|http://localhost:8000/api/v1/reports | Create new Report [User] |
|`DELETE`|http://localhost:8000/api/v1/reports/{id} | Delete Report by ID [Admin] |

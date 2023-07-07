# Springboot-security-jwt-openapi-mockito
This application implemented using spring boot, spring security, JWT token, open API, Mockito, Junit, JPA, H2 database and  lombok.

## To access H2 DB console  
>  URL : http://localhost:8181/h2-console

## Swagger api 
> URL : http://localhost:8181/swagger-ui/index.html

> URL : http://localhost:8181/swagger-api-docs

## To enable debug log level
> **logging.level.com.rajeshkawali=DEBUG**

## Sample json for postman and all api's
```
{
   "firstName":"Rajesh",
   "surname":"Kawali",
   "smoothiePreference":"Strawberry",
   "mobileNumber":9988776655
}
```

#### Add new customer
> POST : http://localhost:8181/api/v1/customer/add

#### Get all customers
> GET : http://localhost:8181/api/v1/customer/getAll

#### Get customer by id
> GET : http://localhost:8181/api/v1/customer/1

#### Update existing customer
> PUT : http://localhost:8181/api/v1/customer/1

#### Delete customer
> DELETE : http://localhost:8181/api/v1/customer/1




## Authentication

#### Register new user
> POST : http://localhost:8181/api/v1/auth/register

```
{
   "firstName":"Rajesh",
   "lastname":"Kawali",
   "email":"rajesh@mail.com",
   "password":"mypassword",
   "role":"USER or ADMIN"
}
```

#### Authenticate user
> POST : http://localhost:8181/api/v1/auth/authenticate

```
{
   "email":"rajesh@mail.com",
   "password":"mypassword"
}
```

#### Refresh the token 
Provide existing token in postman's ->Authentication ->bearer token
> POST : http://localhost:8181/api/v1/auth/refresh-token

#### Logout the user
Provide existing token in postman's ->Authentication ->bearer token
> POST : http://localhost:8181/api/v1/auth/logout



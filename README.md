# My Music 2022

> API responsible for managing user's favorite songs.
---
## 🏆 Meta

Developed by Thiago Souza Gomes


---
# 🖥️ Índice

- [Scope](#-Scope)
- [Technologies used](#-technologies-used)
- [How to use it](#-how-to-use-it)

---

## Scope

● Allow user to search for new music:
1. The service must validate that the user has entered at least 2 characters, returning an HTTP 400 if the query has less than 2 characters.
2. The search must be performed by artist name and song name.
3. Music search should not be case sensitive.
4. The search must return values containing the filter, not needing to inform the full name of the song or artist.
5. The return must be sorted by the artist's name and then by the song's name.

● Allow to add user's favorite songs to playlist:
1. It must receive a request containing the song identifier and the playlist identifier.
2. Must validate that the song identifier and playlist identifier exist.

● Allow to add user's favorite songs to playlist:
1. It must receive a request containing the song identifier and the playlist identifier.
2. Must validate that the song identifier and playlist identifier exist.

All endpoints must have a layer of security to protect the data domain. to implement his security, the endpoints created must require that the requests received have the "authorization" header, containing a valid token to respond to the request. To create and generate the token, use the service
available along with project structure: token-provider-0.0.1-SNAPSHOT.jar.


## 🚀 Technologies used:

The project was developed using:

- JDK 11
- Maven
- Spring Boot
- JPA & Hibernate
- Swagger
- Intellij Idea
- Postman
- CleanArch
- Jacoco
- Microservice

---

## 🧑‍💻 How to use it

- Clone the project contained in this repository In the terminal, run the following command:
```sh
mvn clean install
```

- Run the main class of the project: SummitBootcampApplication

- To test the endpoints use the documentation as a base.
  Acess [Swagger](http://localhost:8000/swagger-ui.html)
  or [aqui](http://localhost:8000/swagger-ui.html)

- To access the base URL of the system, click[aqui](http://localhost:8000/swagger-ui.html)


### token-provider

Download the .jar from the following link to compile the token application

https://drive.google.com/file/d/17weBxIxAE0P1yimKTqBOfocIFi1VMkZQ/view?usp=sharing

To create valid tokens, use the following endpoint:

```
ENDPOINT: /api/v1/token
METODO: POST
BODY: 
{ 
    "data": {
        "name": "fulano"
    }
}
RETORNO: 201 Created
{
    "12321312321312"
}
```

For token validation use the following endpoint:

```
ENDPOINT: /api/v1/token/authorizer
METODO: POST
BODY: 
{ 
    "data": {
        "name": "fulano",
        "token": "12321312321312"
    }
}
RETORNO: 201 Created
{
    "ok"
}
```

# Database

To test the database use the following steps:

To help the development of the API, the initial structure has a pre-defined and populated database.

Modeling:
<div align="center"><img src="https://i.imgur.com/yfMGrur.png" title="source:modelagem imgur" /></div>


Attention:
Id fields using GUID map to string due to complexity in compatibility with Java's native UUID.

Tip:
It is not necessary, but it is possible to use a tool to open and view the MyMusic.db file in an easier way, such as:

https://sqlitestudio.pl

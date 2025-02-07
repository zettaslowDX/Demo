Enlace por defecto Swagger http://localhost:8080/swagger-ui/index.html
Enlace ConsolaH2 http://localhost:8080/h2-console/

En caso de usar los metodos post en swagger quitar las id porque estan autogeneradas
Ejemplo de post para usuario
{
"nombre": "string",
"email": "string",
"telefono": "string",
"fechaRegistro": "2025-02-06"
}

Ejemplo de post para libro
{
"titulo": "string",
"autor": "string",
"isbn": "string",
"fechaPublicacion": "2025-02-06"
}

Ejemplo de post para prestamo
{
"libro": {
"id": 1,
"titulo": "string",
"autor": "string",
"isbn": "string",
"fechaPublicacion": "2025-02-06"
},
"usuario": {
"id": 1,
"nombre": "string",
"email": "string",
"telefono": "string",
"fechaRegistro": "2025-02-06"
},
"fechaPrestamo": "2025-02-06",
"fechaDevolucion": "2025-02-06"
}


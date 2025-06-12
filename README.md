# gestor-empleados
royecto desarrollado con Spring Boot para la gestión de empleados, integrando base de datos MySQL, servicios REST y consumo de servicios SOAP.


## Tecnologías utilizadas

- Java 17
- Spring Boot 3.1.0
- Spring Data JPA
- Spring Web y Web Services
- MySQL
- Maven
- JAXB / JAX-WS (para servicios SOAP)
- Lombok


## Cómo ejecutar el proyecto

1. Clonar repositorio
```
git clone https://github.com/wyllo92/gestor-empleados.git
cd gestor-empleados
```

2. **Configurar la base de datos:**
Asegúrate de tener un servidor MySQL ejecutándose en el puerto 3306, creada una base de datos llamada `empleados_db` con el usuario `root` y contraseña `root`


4. **Ejecutar el proyecto:**

```
mvn spring-boot:run
```

## Generación de código SOAP

El proyecto usa el plugin `jaxws-maven-plugin` para generar clases a partir de archivos WSDL ubicados en `src/main/resources`. Estas clases se generarán en:

```
target/generated-sources/jaxws
```

El paquete destino para las clases generadas es:  
`com.app.gestor_empleados.soap.model`

Para ejecutar la generación de código WSDL:

```
mvn clean compile
```

para probar en postman 
pon este endpoint con el metodo POST version SOAP:
```
http://localhost:8080/api/empleados/soap
```

envia este JSON en el body:
```
{
   "numeroDocumento":"456123",
   "nombres":"William",
   "apellidos":"Arias Benavides",
   "tipoDocumento":"CC",
   "fechaNacimiento":"2005-05-15",
   "fechaVinculacion":"2020-03-01",
   "cargo":"Desarrollador",
   "salario":8000000.0
}
```

tambien esta este endpoint para el REST:
```
http://localhost:8080/api/empleados
```

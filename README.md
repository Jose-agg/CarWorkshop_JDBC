# CarWorkshop #

Este repositorio contiene el código fuente para la gestión de un taller de coches. Esta aplicación se centra en el correcto uso de patrones y metodologías para el acceso y tratamiento datos.

Para su desarrollo se han tomado las siguientes decisiones:
- Capa de presentación: interacción con el usuario.
- Service Layer (SL): punto de entrada desde la presentación a la capa de negocio.
- Transaction Script (TS): capa de negocio. Se crea una clase por cada caso de uso.
- Table Data Gateway (TDG): se encarga de la persistencia. Hay una clase por cada entidad/tabla
- Factory Method: se encarga de crear y devolver las instancias de los servicios para así evitar que las capas superiores conozcan la implementación del servicio.

## Anotaciones ##
- Es necesaria la librería que está dentro de **librerias > AlbUtil.zip** para que funcione la aplicación.
- Son necesarios los .jar de hsqldb y ojdbc6, se encuentran en el archivo **librerias > bbdd.zip**.

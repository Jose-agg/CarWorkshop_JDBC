package uo.ri.persistence;

import java.sql.Connection;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Vehiculos (VehiculosGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface MediosPagoGateway {

	/**
	 * Metodo que establece la conexión de la base de datos con la 
	 * clase de persistencia
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	/**
	 * Metodo que añade la forma de pago en metalico a un cliente
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 */
	public void addMetalicoById(Long idCliente);

	/**
	 * Metodo que borra la forma de pago en metalico de un cliente
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 */
	public void deleteMetalicoById(Long idCliente);

}

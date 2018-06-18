package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

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

	/**
	 * Metodo que devuelve la lista de medios de pago que tiene disponible un 
	 * cliente.
	 * 
	 * @param idCliente Identificador del cliente
	 * @return lista de medios de pago
	 */
	public List<Map<String, Object>> listMediosPagoByIiCliente(Long idCliente);

	/**
	 * Actualizar el medio de pago, del tipo bono, con los datos que se pasan
	 * como parametros
	 * 
	 * @param idMedioPago Identificador del medio de pago
	 * @param nuevoAcumulado Nuevo valor del acumulado
	 * @param disponible Nuevo valor disponible
	 */
	public void updateEstadoMedioPago(Long idMedioPago, double nuevoAcumulado,
			double disponible);

	/**
	 * Actualizar el medio de pago, del tipo metalico o tarjeta, con los datos
	 * que se pasan como parametros
	 * 
	 * @param idMedioPago Identificador del medio de pago
	 * @param nuevoAcumulado Nuevo valor del acumulado
	 */
	public void updateEstadoMedioPago(Long idMedioPago, double nuevoAcumulado);

}

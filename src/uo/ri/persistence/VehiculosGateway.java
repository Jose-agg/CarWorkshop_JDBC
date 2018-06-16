package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Vehiculos (VehiculosGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface VehiculosGateway {

	/**
	 * Metodo que establece la conexión de la base de datos con la 
	 * clase de persistencia
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	/**
	 * Metodo que devuelve la lista de vehiculos que tiene un determinado 
	 * cliente
	 * 
	 * @param cliente Identificador del cliente a buscar
	 * @return lista de vehiculos del cliente
	 */
	public List<Long> getVehiculosPorCliente(Long cliente);

}

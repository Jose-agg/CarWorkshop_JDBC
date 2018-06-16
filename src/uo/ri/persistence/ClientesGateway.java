package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Clientes (ClientesGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface ClientesGateway {

	/**
	 * Metodo que establece la conexión de la base de datos con la clase 
	 * de persistencia
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	/**
	 * Metodo que devuelve los identificadores de todos los clientes
	 * 
	 * @return lista de identificadores de clientes
	 */
	public List<Long> getListaClientes();

}

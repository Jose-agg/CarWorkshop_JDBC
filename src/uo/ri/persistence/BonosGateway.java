package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Bonos (BonosGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface BonosGateway {

	/**
	 * Metodo que establece la conexión de la base de datos con la clase 
	 * de persistencia
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	/**
	 * Metodo que devuelve el codigo del ultimo bono creado
	 * 
	 * @return el codigo del ultimo bono
	 */
	public int getUltimoCodigo();

	/**
	 * Metodo que añade un bono al cliente que se le pasa como parametro, con 
	 * el codigo, dinero disponible y descripcion que se desee.
	 * 
	 * @param code Codigo del bono
	 * @param cantidad Cantidad de dinero del bono
	 * @param cliente Cliente al que se le asignará el bono
	 * @param descripcion Descripcion del bono
	 */
	public void insertarBonosMedioPago(String code, int cantidad, Long cliente,
			String descripcion);

	/**
	 * Metodo que devuelve una lista con todos los bonos de un cliente
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 * @return lista de bonos del cliente
	 */
	public List<Map<String, Object>> findBonosCliente(Long idCliente);

}

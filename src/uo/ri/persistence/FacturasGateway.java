package uo.ri.persistence;

import java.sql.Connection;
import java.util.Map;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Facturas (FacturasGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface FacturasGateway {

	/**
	 * Establece la conexión de la base de datos con la clase de persistencia,
	 *  para
	 * poder realizar las consultas
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	/**
	 * Método que obtiene el numero con el que se ha guardado la ultima factura
	 * @return El numero que le corresponde a la siguiente factura
	 */
	public Long getLastInvoiceNumber();

	public long crearFactura(Map<String, Object> mapa);

}

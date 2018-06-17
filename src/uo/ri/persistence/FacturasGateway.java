package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
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
	 * para poder realizar las consultas
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	/**
	 * Método que obtiene el numero con el que se ha guardado la ultima factura
	 * 
	 * @return El numero que le corresponde a la siguiente factura
	 */
	public Long getLastInvoiceNumber();

	/**
	 * Metodo que guarda en la base de datos una nueva factura
	 * 
	 * @param mapa
	 * @return
	 */
	public long crearFactura(Map<String, Object> mapa);

	/**
	 * Metodo que recibe como parametro un cliente y devuelve todas sus facturas
	 * con importe superior a 500€, que no hayan sido utilizadas en bonos y que
	 * ya hayan sido cobradas
	 * 
	 * @param cliente Identificador del cliente a buscar
	 * @return lista de facturas
	 */
	public List<Long> getFacturasPorClienteImporte500NoUsadas(Long cliente);

	/**
	 * Metodo que actualizada la propiedad usada_bono de una factura, declarando
	 * así que esta factura ya ha sido utilizada para un bono.
	 * 
	 * @param factura Identificador de la factura
	 */
	public void actualizarFacturaUsadaBono(Long factura);

}

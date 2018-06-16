package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Averia (AveriasGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface AveriasGateway {

	/**
	 * Metodo que establece la conexión de la base de datos con la clase de 
	 * persistencia
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	/**
	 * Método que se encarga de buscar una avería en la base de datos a partir 
	 * de su id
	 * 
	 * @param idAveria El id de la avería a buscar
	 * 
	 * @return Devuelve la avería si existe en la base de datos. Si no existe
	 *         devuelve null
	 */
	public Map<String, Object> findByID(Long idAveria);

	/**
	 * Método que devuelve el importe de la mano de obra de la avería
	 * 
	 * @param idAveria El id de la avería de la que se desea conocer el importe
	 * @return El importe de la mano de obra
	 */
	public double consultaImporteManoObra(Long idAveria);

	/**
	 * Método que devuelve el importe de las sustituciones de repuestos de una
	 * avería
	 * 
	 * @param idAveria El id de la avería de la que se desea conocer el importe 
	 * 			de sus repuestos
	 * @return El importe de las sustituciones de repuestos
	 */
	public double consultaImporteRepuestos(Long idAveria);

	/**
	 * Actualiza los datos de una avería pasada por parámetro
	 * 
	 * @param idAveria El id de la avería que se desea actualizar
	 * @param mapa Los nuevos datos para la avería
	 */
	public void actualizarAveria(Long idAveria, Map<String, Object> averia);

	/**
	 * Metodo que devuelve las averias no utilizadas para generar bonos de un 
	 * vehiculo
	 * 
	 * @param vehiculo Identificador del vehiculo a buscar
	 * @return lista de averias
	 */
	public List<Long> getAveriasNoUsadas(Long vehiculo);

	/**
	 * Metodo que actualizada la propiedad usada_bono de una averia, declarando
	 * así que esta averia ya ha sido utilizada.
	 * 
	 * @param long1
	 */
	public void actualizarAveriaUsadaBono(Long idAveria);

}

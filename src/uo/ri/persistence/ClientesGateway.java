package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

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

	/**
	 * Metodo que devuelve los datos de un cliente
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 * @return mapa con los datos del cliente. Si no encuentra a un cliente con
	 * 			dicho id devuelve null
	 */
	public Map<String, Object> getDetallesCliente(Long idCliente);

	/**
	 * Metodo que devuelve el id de un cliente a partir de su dni
	 * 
	 * @param dni El dni del cliente a buscar
	 * @return el identificador del cliente
	 */
	public Long getClienteID(String dni);

	/**
	 * Metodo encargado de crear un nuevo cliente en el sistema. El cliente 
	 * viene recomendado
	 * 
	 * @param dni DNI del nuevo cliente
	 * @param nombre Nombre del nuevo cliente
	 * @param apellidos Apellidos del nuevo cliente
	 * @param zipcode Codigo Postal del nuevo cliente
	 * @param telefono Telefono del nuevo cliente
	 * @param email Email del nuevo cliente
	 * @param recomendador Identificador del cliente que recomendo al nuevo 
	 * 			cliente
	 * @throws BusinessException
	 */
	public void addCliente(String dni, String nombre, String apellidos,
			String zipcode, String telefono, String email, Long recomendador);

	/**
	 * Metodo encargado de crear un nuevo cliente en el sistema. El cliente 
	 * no viene recomendado
	 * 
	 * @param dni DNI del nuevo cliente
	 * @param nombre Nombre del nuevo cliente
	 * @param apellidos Apellidos del nuevo cliente
	 * @param zipcode Codigo Postal del nuevo cliente
	 * @param telefono Telefono del nuevo cliente
	 * @param email Email del nuevo cliente
	 * 
	 * @throws BusinessException
	 */
	public void addCliente(String dni, String nombre, String apellidos,
			String zipcode, String telefono, String email);

	/**
	 * Metodo que comprueba si el cliente tiene facturas abonadas o no
	 * 
	 * @param idRecomendador Identificador del cliente
	 * @return true si tiene facturas abonadas, false si no.
	 */
	public boolean comprobarClienteConFacturasAbonadas(Long idRecomendador);

	/**
	 * Metodo que borra un cliente del sistema
	 * 
	 * @param idCliente Identificador del cliente
	 */
	public void deleteClient(Long idCliente);

	/**
	 * Metodo que devuelve el id, nombre, apellidos y dni de todos los clientes
	 * 
	 * @return lista de datos de clientes
	 */
	public List<Map<String, Object>> findAllClients();

	/**
	 * Metodo que devuelve el nombre, los apellidos y el id de todos los 
	 * usuarios recomendados por un cliente
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 * @return el mapa con los clientes que ha recomendado
	 */
	public Map<String, Object> findRecomendedClients(Long idCliente);

	/**
	 * Metodo que actualiza los datos basicos de un cliente. No se cambiará ni 
	 * su dni ni su identificador
	 * 
	 * @param idCliente Identificador del cliente
	 * @param nombre Nuevo nombre del cliente
	 * @param apellidos Nuevo apellidos del cliente
	 * @param zipcode Nuevo codigo postal del cliente
	 * @param telefono Nuevo telefono del cliente
	 * @param email Nuevo email del cliente
	 */
	public void updateClient(Long idCliente, String nombre, String apellidos,
			String zipcode, String telefono, String email);

	/**
	 * Metodo que devuelve el id de un cliente a partir del id de una factura
	 * 
	 * @param idFactura Identificador de la factura a buscar
	 * @return identificador del cliente
	 */
	public Long findIdClienteByIdFactura(Long idFactura);
}

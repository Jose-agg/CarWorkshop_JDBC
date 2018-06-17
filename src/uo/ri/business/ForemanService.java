package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * logica asociada al jefe de taller (ForemanGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface ForemanService {

	/**
	 * Metodo encargado de crear un nuevo cliente en el sistema
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
	public void newClient(String dni, String nombre, String apellidos,
			String zipcode, String telefono, String email, String recomendador)
			throws BusinessException;

	/**
	 * Metodo encargado de borrar un cliente del sistema, al igual que su 
	 * metálico y su recomendación
	 * 
	 * @param idCliente Identificador del cliente a borrar
	 * @throws BusinessException
	 */
	public void deleteClient(String idCliente) throws BusinessException;

	/**
	 * Metodo encargado de recuperar los detalles de un cliente
	 * 
	 * @param idCliente Identificador del cliente a borrar
	 * @return mapa con los detalles
	 * @throws BusinessException
	 */
	public Map<String, Object> detailClient(String idCliente)
			throws BusinessException;

	/**
	 * Metodo que recupera toda la lista de clientes que hay en el sistema
	 * 
	 * @return lista de clientes
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> listClients() throws BusinessException;

	/**
	 * Metodo que devuelve todos los clientes que hayan sido recomendados por
	 * el cliente que se pase como parametros
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 * @return mapa con los clientes y sus identificadores
	 * @throws BusinessException
	 */
	public Map<String, Object> listRecomendationsClient(String idCliente)
			throws BusinessException;

	/**
	 * Metodo encargado de actualizar los datos de un cliente del sistema
	 * 
	 * @param idCliente Identificador del cliente a modificar
	 * @param dni Nuevo DNI del cliente
	 * @param nombre Nuevo nombre del cliente
	 * @param apellidos Nuevo apellidos del cliente
	 * @param zipcode Nuevo zipcode del cliente
	 * @param telefono Nuevo telefono del cliente
	 * @param email Nuevo email del cliente
	 * 
	 * @throws BusinessException
	 */
	public void updateClient(String idCliente, String dni, String nombre,
			String apellidos, String zipcode, String telefono, String email)
			throws BusinessException;

}

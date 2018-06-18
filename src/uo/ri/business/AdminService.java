package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * logica asociada al administrador (AdminServiceImpl)
 * 
 * @author José Antonio García García
 */
public interface AdminService {

	public void newMechanic(String nombre, String apellidos)
			throws BusinessException;

	public void deleteMechanic(Long idMecanico) throws BusinessException;

	public void updateMechanic(Long idMecanico, String nombre, String apellidos)
			throws BusinessException;

	public List<Map<String, Object>> findAllMechanics()
			throws BusinessException;

	/**
	 * Metodo encargado de generar los bonos correspondientes a los clientes 
	 * con 3 o mas averias abonadas
	 * 
	 * @throws BusinessException
	 */
	public void generarBonosNumeroAverias() throws BusinessException;

	/**
	 * Metodo encargado de generar los bonos correspondientes a los clientes
	 * con facturas ya abonadas con importe superior a los 500€
	 * 
	 * @throws BusinessException
	 */
	public void generarBonosCantidadFacturas() throws BusinessException;

	/**
	 * Metodo encargado de devolver la lista de bonos de un cliente e 
	 * informacion agregada
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 * @return lista de bonos del cliente
	 */
	public List<Map<String, Object>> mostrarDetallesBonoCliente(
			String idCliente) throws BusinessException;

	/**
	 * Metodo que devuelve la lista de clientes con informacion agregada de los
	 * bonos que tiene
	 * 
	 * @return lista de clientes con informacion agregada
	 */
	public List<Map<String, Object>> listarResumenBonos()
			throws BusinessException;
}

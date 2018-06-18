package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Mecanicos (MecanicosGatewayImpl)
 * 
 * @author José Antonio García García
 */
public interface MecanicosGateway {

	/**
	 * Metodo que establece la conexión de la base de datos con la clase 
	 * de persistencia
	 * 
	 * @param connection La conexión con la base de datos
	 */
	public void setConnection(Connection connection);

	public void addMechanic(String nombre, String apellidos);

	public void deleteMechanic(Long idMecanico);

	public void updateMechanic(Long idMecanico, String nombre,
			String apellidos);

	public List<Map<String, Object>> findAllMechanics();

}

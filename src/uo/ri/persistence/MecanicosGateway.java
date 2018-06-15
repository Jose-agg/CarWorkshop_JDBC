package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface MecanicosGateway {

	public void setConnection(Connection connection);

	public void addMechanic(String nombre, String apellidos);

	public void deleteMechanic(Long idMecanico);

	public void updateMechanic(Long idMecanico, String nombre, String apellidos);

	public List<Map<String, Object>> findAllMechanics();

}

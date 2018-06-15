package uo.ri.business;

import java.util.List;
import java.util.Map;

public interface AdminService {

	public void newMechanic(String nombre, String apellidos);

	public void deleteMechanic(Long idMecanico);

	public void updateMechanic(Long idMecanico, String nombre, String apellidos);

	public List<Map<String, Object>> findAllMechanics();
}

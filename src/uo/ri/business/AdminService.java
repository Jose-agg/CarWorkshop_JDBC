package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

public interface AdminService {

	public void newMechanic(String nombre, String apellidos)
			throws BusinessException;

	public void deleteMechanic(Long idMecanico) throws BusinessException;

	public void updateMechanic(Long idMecanico, String nombre, String apellidos)
			throws BusinessException;

	public List<Map<String, Object>> findAllMechanics()
			throws BusinessException;

	public void generarBonosNumeroAverias() throws BusinessException;

	public void generarBonosCantidadFacturas() throws BusinessException;
}

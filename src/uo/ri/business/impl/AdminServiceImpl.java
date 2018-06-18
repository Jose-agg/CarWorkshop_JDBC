package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.bonos.GenerarBonosCantidadFacturas;
import uo.ri.business.impl.admin.bonos.GenerarBonosNumeroAverias;
import uo.ri.business.impl.admin.bonos.ListarResumenBonos;
import uo.ri.business.impl.admin.bonos.MostrarDetallesBonoCliente;
import uo.ri.business.impl.admin.mecanicos.AddMechanic;
import uo.ri.business.impl.admin.mecanicos.DeleteMechanic;
import uo.ri.business.impl.admin.mecanicos.FindAllMechanics;
import uo.ri.business.impl.admin.mecanicos.UpdateMechanic;
import uo.ri.common.BusinessException;

public class AdminServiceImpl implements AdminService {

	@Override
	public void newMechanic(String nombre, String apellidos)
			throws BusinessException {
		AddMechanic addMechanic = new AddMechanic(nombre, apellidos);
		addMechanic.execute();
	}

	@Override
	public void deleteMechanic(Long idMecanico) throws BusinessException {
		DeleteMechanic deleteMechanic = new DeleteMechanic(idMecanico);
		deleteMechanic.execute();
	}

	@Override
	public void updateMechanic(Long idMecanico, String nombre, String apellidos)
			throws BusinessException {
		UpdateMechanic updateMechanic = new UpdateMechanic(idMecanico, nombre,
				apellidos);
		updateMechanic.execute();
	}

	@Override
	public List<Map<String, Object>> findAllMechanics()
			throws BusinessException {
		FindAllMechanics findAllMechanics = new FindAllMechanics();
		return findAllMechanics.execute();
	}

	@Override
	public void generarBonosNumeroAverias() throws BusinessException {
		GenerarBonosNumeroAverias bonosNumeroAverias = new GenerarBonosNumeroAverias();
		bonosNumeroAverias.execute();
	}

	@Override
	public void generarBonosCantidadFacturas() throws BusinessException {
		GenerarBonosCantidadFacturas bonosCantidadFacturas = new GenerarBonosCantidadFacturas();
		bonosCantidadFacturas.execute();
	}

	@Override
	public List<Map<String, Object>> mostrarDetallesBonoCliente(
			String idCliente) throws BusinessException {
		MostrarDetallesBonoCliente mostrarDetallesBonoCliente = new MostrarDetallesBonoCliente(
				idCliente);
		return mostrarDetallesBonoCliente.execute();
	}

	@Override
	public List<Map<String, Object>> listarResumenBonos()
			throws BusinessException {
		ListarResumenBonos listarResumenBonos = new ListarResumenBonos();
		return listarResumenBonos.execute();
	}

}

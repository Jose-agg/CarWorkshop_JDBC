package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.AddMechanic;
import uo.ri.business.impl.admin.DeleteMechanic;
import uo.ri.business.impl.admin.FindAllMechanics;
import uo.ri.business.impl.admin.UpdateMechanic;

public class AdminServiceImpl implements AdminService {

	@Override
	public void newMechanic(String nombre, String apellidos) {
		AddMechanic addMechanic = new AddMechanic(nombre, apellidos);
		addMechanic.execute();
	}

	@Override
	public void deleteMechanic(Long idMecanico) {
		DeleteMechanic deleteMechanic = new DeleteMechanic(idMecanico);
		deleteMechanic.execute();
	}

	@Override
	public void updateMechanic(Long idMecanico, String nombre, String apellidos) {
		UpdateMechanic updateMechanic = new UpdateMechanic(idMecanico, nombre, apellidos);
		updateMechanic.execute();
	}

	@Override
	public List<Map<String, Object>> findAllMechanics() {
		FindAllMechanics findAllMechanics = new FindAllMechanics();
		return findAllMechanics.execute();
	}

}

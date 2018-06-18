package uo.ri.ui.admin.action.mecanicos;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Pedir datos
		Long idMecanico = Console.readLong("Id del mecánico");
		String nombre = Console.readString("Nombre");
		String apellidos = Console.readString("Apellidos");

		AdminService adminService = ServicesFactory.getAdminService();
		adminService.updateMechanic(idMecanico, nombre, apellidos);

		// Mostrar resultado
		Printer.printUpdateMechanic();

	}

}

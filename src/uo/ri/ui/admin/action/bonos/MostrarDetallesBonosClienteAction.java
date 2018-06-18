package uo.ri.ui.admin.action.bonos;

import java.util.List;
import java.util.Map;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para mostrar los detalles de los bonos que le 
 * corresponden a un cliente
 * 
 * @author José Antonio García García
 *
 */
public class MostrarDetallesBonosClienteAction implements Action {

	@Override
	public void execute() throws BusinessException {

		String idCliente = Console.readString("ID cliente");

		AdminService adminService = ServicesFactory.getAdminService();
		List<Map<String, Object>> listaBonos = adminService
				.mostrarDetallesBonoCliente(idCliente);

		Printer.printMostrarDetallesBonosCliente(listaBonos);
	}

}

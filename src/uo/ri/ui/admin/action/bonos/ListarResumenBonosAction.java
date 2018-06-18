package uo.ri.ui.admin.action.bonos;

import java.util.List;
import java.util.Map;

import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para mostrar la lista de clientes con los bonos 
 * que tienen e informacion agregada
 * 
 * @author José Antonio García García
 *
 */
public class ListarResumenBonosAction implements Action {

	@Override
	public void execute() throws BusinessException {

		AdminService adminService = ServicesFactory.getAdminService();
		List<Map<String, Object>> listaClientes = adminService
				.listarResumenBonos();

		Printer.printListarResumenBonos(listaClientes);
	}
}

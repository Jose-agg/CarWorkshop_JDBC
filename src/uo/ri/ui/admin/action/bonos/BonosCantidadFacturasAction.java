package uo.ri.ui.admin.action.bonos;

import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para la generación automática
 * de bonos por facturas superiores a 500€
 * 
 * @author José Antonio García García
 *
 */
public class BonosCantidadFacturasAction implements Action {

	@Override
	public void execute() throws BusinessException {
		AdminService adminService = ServicesFactory.getAdminService();
		adminService.generarBonosCantidadFacturas();

		Printer.printBonosCantidadFacturas();

	}
}
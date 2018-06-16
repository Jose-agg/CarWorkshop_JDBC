package uo.ri.ui.admin.action.bonos;

import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para la generación automática
 * de bonos por cada 3 averias.
 * 
 * @author José Antonio García García
 *
 */
public class BonosNumeroAveriasAction implements Action {

	@Override
	public void execute() throws BusinessException {
		AdminService adminService = ServicesFactory.getAdminService();
		adminService.generarBonosNumeroAverias();

		Printer.printBonosNumeroAverias();

	}
}

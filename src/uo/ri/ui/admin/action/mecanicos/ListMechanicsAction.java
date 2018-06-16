package uo.ri.ui.admin.action.mecanicos;

import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ListMechanicsAction implements Action {

	@Override
	public void execute() throws BusinessException {
		AdminService adminService = ServicesFactory.getAdminService();

		Printer.printListMechanics(adminService.findAllMechanics());

	}
}

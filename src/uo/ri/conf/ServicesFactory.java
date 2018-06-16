package uo.ri.conf;

import uo.ri.business.AdminService;
import uo.ri.business.CashService;
import uo.ri.business.ForemanService;
import uo.ri.business.MechanicService;
import uo.ri.business.impl.AdminServiceImpl;
import uo.ri.business.impl.CashServiceImpl;

/**
 * Clase resultante de aplicar el patron factoría que permite 
 * crear instancias de los servicios que serán solicitados por la 
 * capa de presentación
 * 
 * @author José Antonio García García
 *
 */
public class ServicesFactory {

	/**
	 * Devuelve una instancia del servicio de administrador
	 * 
	 * @return Instancia del servicio de administrador
	 */
	public static AdminService getAdminService() {
		return new AdminServiceImpl();
	}

	/**
	 * Devuelve una instancia del servicio de la caja
	 * 
	 * @return Instancia del servicio de la caja
	 */
	public static CashService getCashService() {
		return new CashServiceImpl();
	}

	public static MechanicService getMechanicService() {
		return null;
	}

	public static ForemanService getForemanService() {
		return null;
	}
}

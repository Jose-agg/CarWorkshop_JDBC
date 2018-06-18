package uo.ri.ui.cash.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CashService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para la liquidación de las facturas
 * 
 * @author José Antonio García García
 *
 */
public class LiquidarFacturaAction implements Action {

	CashService cashService;

	@Override
	public void execute() throws BusinessException {

		String numFactura = Console.readString("Numero de factura");

		cashService = ServicesFactory.getCashService();
		List<Map<String, Object>> mediosPago = cashService
				.findMetodosPagoFactura(numFactura);

		Printer.printMediosPago(mediosPago);

		payInvoice(mediosPago, numFactura);

		Printer.printLiquidarFactura();
	}

	/**
	 * Metodo auxiliar que pregunta al usuario por los medios de pago que 
	 * quiere usar y, una vez, completado el total del importe de la factura 
	 * la liquida
	 * 
	 * @param mediosPago Medios de pago que el cliente tiene disponible
	 * @param numFactura 
	 * @throws BusinessException 
	 */
	private void payInvoice(List<Map<String, Object>> mediosPago,
			String numFactura) throws BusinessException {

		Map<String, String> formatoPagos = new HashMap<String, String>();
		Map<String, Object> factura;
		double total, restante, pagado = 0;
		String eleccion, cantidad;

		factura = cashService.getDetallesFactura(numFactura);
		total = (Double) factura.get("importe");

		Printer.printRecaudarMediosPago("Total", total);
		do {
			Printer.printRecaudarMediosPago("Restante", total - pagado);
			eleccion = Console.readString("Selecciona el numero del medio de"
					+ " pago que desea utilizar");
			cantidad = Console.readString(
					"Selecciona la cantidad que desea pagar con este medio");
			formatoPagos.put(eleccion, cantidad);
			restante = cashService.checkTotalFactura(factura, formatoPagos,
					mediosPago);
			pagado = total - restante;
		} while (restante != 0);
	}
}

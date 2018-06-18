package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.CashService;
import uo.ri.business.impl.cash.CheckTotalFactura;
import uo.ri.business.impl.cash.CreateInvoiceFor;
import uo.ri.business.impl.cash.DetallesFactura;
import uo.ri.business.impl.cash.FindMetodosPagoFactura;
import uo.ri.common.BusinessException;

public class CashServiceImpl implements CashService {

	@Override
	public Map<String, Object> createInvoiceFor(List<Long> idsAveria)
			throws BusinessException {
		CreateInvoiceFor createInvoiceFor = new CreateInvoiceFor(idsAveria);
		return createInvoiceFor.execute();
	}

	@Override
	public List<Map<String, Object>> findMetodosPagoFactura(String numFactura)
			throws BusinessException {
		FindMetodosPagoFactura findMetodosPagoFactura = new FindMetodosPagoFactura(
				numFactura);
		return findMetodosPagoFactura.execute();
	}

	@Override
	public Map<String, Object> getDetallesFactura(String numFactura)
			throws BusinessException {
		DetallesFactura detallesFactura = new DetallesFactura(numFactura);
		return detallesFactura.execute();
	}

	@Override
	public Double checkTotalFactura(Map<String, Object> factura,
			Map<String, String> formatoPagos,
			List<Map<String, Object>> mediosPago) throws BusinessException {
		CheckTotalFactura checkTotalFactura = new CheckTotalFactura(factura,
				formatoPagos, mediosPago);
		return checkTotalFactura.execute();
	}

}

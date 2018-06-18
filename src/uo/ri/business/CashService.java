package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * logica asociada a la caja (CashServiceImpl)
 * 
 * @author José Antonio García García
 */
public interface CashService {

	public Map<String, Object> createInvoiceFor(List<Long> list)
			throws BusinessException;

	/**
	 * Metodo que devuelve las formas de pago que tiene disponibles el cliente
	 * de una factura. Se entiende que un cliente no puede pagar la factura de 
	 * otro cliente.
	 * 
	 * @param numFactura Numero de la factura
	 * @return lista de formas de pago
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> findMetodosPagoFactura(String numFactura)
			throws BusinessException;

	/**
	 * Metodo que devuelve todos los datos de una factura
	 * 
	 * @param numFactura Numero de la factura a buscar
	 * @return datos de la factura
	 * @throws BusinessException
	 */
	public Map<String, Object> getDetallesFactura(String numFactura)
			throws BusinessException;

	/**
	 * Metodo que comprueba si se puede liquidar una factura utilizando los 
	 * formatos y valores de pagos que se haya especificado. Si se iguala el 
	 * importe total de la factura esta pasa a estar 'ABONADA' y se modifican 
	 * todos los medios de pago para que concuerden con los nuevos datos
	 * 
	 * @param factura Datos de la factura
	 * @param formatoPagos Lista de formas de pagar que se han seleccionado así 
	 * 			como la cantidad que se quiere sacar de cada una de ellas
	 * @param mediosPago lista de medios de pago que tiene el cliente
	 * @return devuelve lo que falta por pagar. Si devuelve 0 es que la deuda 
	 * 			esta pagada
	 * @throws BusinessException
	 */
	public Double checkTotalFactura(Map<String, Object> factura,
			Map<String, String> formatoPagos,
			List<Map<String, Object>> mediosPago) throws BusinessException;

}

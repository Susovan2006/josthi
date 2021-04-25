package com.josthi.web.dao.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.bo.ServiceInvoiceBean;
import com.josthi.web.utils.Utils;

public class ServiceInvoiceDetailRowMapper implements RowMapper<ServiceInvoiceBean> {
	@Override
	
	//MMMMM d, yyyy
	//Utils.timestampToFormattedString(resultSet.getTimestamp("PURCHASE_DATE"), "MMMMM d, yyyy")
public ServiceInvoiceBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		ServiceInvoiceBean serviceInvoiceBean= new ServiceInvoiceBean() ;
		serviceInvoiceBean.setInvoiceId(resultSet.getString("TICKET_NO"));
		serviceInvoiceBean.setServiceRequestedBy(resultSet.getString("REQUESTED_BY"));
		serviceInvoiceBean.setServiceRequestedFor(resultSet.getString("REQUESTED_FOR"));
		serviceInvoiceBean.setServiceAssignedTo(resultSet.getString("ASSIGNED_TO"));
		serviceInvoiceBean.setServiceRequestedOn(Utils.timestampToFormattedString(resultSet.getTimestamp("REQUESTED_ON"), "MMMMM d, yyyy"));
		serviceInvoiceBean.setServiceType(resultSet.getString("SERVICE_TYPE"));
		serviceInvoiceBean.setServiceCode(resultSet.getString("SERVICE_CATEGORY"));
		serviceInvoiceBean.setServiceDescription(resultSet.getString("SERVICE_REQ_DESCRIPTION"));
		serviceInvoiceBean.setServicePurchaseDate(Utils.timestampToFormattedString(resultSet.getTimestamp("PURCHASE_DATE"), "MMMMM d, yyyy"));
		serviceInvoiceBean.setPaymentStatus(resultSet.getString("PAYMENT_STATUS"));
		serviceInvoiceBean.setPaymentId(resultSet.getString("PAYMENT_INVOICE_ID"));
		serviceInvoiceBean.setFinalPrice(resultSet.getString("PRICE_IN_INR"));	
		return serviceInvoiceBean;
		}
	}
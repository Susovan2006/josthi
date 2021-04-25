package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PurchaseHistoryBean;
import com.josthi.web.utils.Utils;

public class PurchaseHistoryRowmapper implements RowMapper<PurchaseHistoryBean> {
	
	public PurchaseHistoryBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		PurchaseHistoryBean purchaseHistoryBean= new PurchaseHistoryBean() ;

		String purchaseId = resultSet.getString("PURCHASE_ID_TKT");
		
		purchaseHistoryBean.setPurchaseId(purchaseId);
		
		if(purchaseId.startsWith("P")) {
			purchaseHistoryBean.setPurchaseItem(resultSet.getString("PURCHASE_ITEM")+" Plan");
		}else {
			purchaseHistoryBean.setPurchaseItem("Service :"+resultSet.getString("PURCHASE_ITEM"));
		}
		
		
		String purchaseDetails = resultSet.getString("PURCHASE_DETAILS");		
		purchaseHistoryBean.setPurchaseDetails(purchaseDetails);
		
		purchaseHistoryBean.setPurchaseDate(Utils.timestampToFormattedString(resultSet.getTimestamp("PURCHASE_DATE"), "d MMM yyyy"));
		
		purchaseHistoryBean.setPaymentStatus(resultSet.getString("PAYMENT_STATUS"));
		purchaseHistoryBean.setPaymentId(resultSet.getString("PAYMENT_INVOICE_ID"));
		purchaseHistoryBean.setPriceInInr(resultSet.getString("PRICE_IN_INR"));
				
		return purchaseHistoryBean;
		}

}

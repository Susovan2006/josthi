package com.josthi.web.dao.rowmapper;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.OnDemandServiceBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.utils.Utils;

public class OnDemandServiceDetailRowMapper implements RowMapper<OnDemandServiceBean> {
	@Override
public OnDemandServiceBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		OnDemandServiceBean onDemandServiceBean= new OnDemandServiceBean() ;
		onDemandServiceBean.setId(resultSet.getInt("ID"));
		onDemandServiceBean.setServiceName(resultSet.getString("SERVICE_NAME"));
		onDemandServiceBean.setDescription(resultSet.getString("DESCRIPTION"));
		onDemandServiceBean.setServiceCode(resultSet.getString("SERVICE_CODE"));
		onDemandServiceBean.setServiceType(resultSet.getString("SERVICE_TYPE"));
		BigDecimal actualPrice = resultSet.getBigDecimal("ON_DEMANT_PRICE_INR");
		onDemandServiceBean.setOnDemantPriceInr(actualPrice);
		
		double actualPriceDouble = actualPrice.doubleValue();
		int discountPercentage = 20;
		double discount = ((double) discountPercentage/100) * actualPriceDouble;
		double cutOutPrice = actualPriceDouble + discount;
		onDemandServiceBean.setCutOutPriceStr(Utils.formattedCurrency(cutOutPrice+""));
		onDemandServiceBean.setOnDemandPriceActualStr(Utils.formattedCurrency(actualPriceDouble+""));
		onDemandServiceBean.setDisclaimer(resultSet.getString("DISCLAIMER"));
		onDemandServiceBean.setOnDemandImage(resultSet.getString("ONDEMAND_IMAGE"));
		
		float rating = resultSet.getFloat("ONDEMAND_RATING");
		if(rating == 0 || rating > 5) {
			onDemandServiceBean.setOnDemandReview(Constant.DEFAULT_USER_RATING);
		}else {
			onDemandServiceBean.setOnDemandReview(resultSet.getFloat("ONDEMAND_RATING"));
		}
		
		
		onDemandServiceBean.setStarRating(Utils.CalculateStarRating(rating));
		onDemandServiceBean.setOnDemandInfo(resultSet.getString("ONDEMAND_INFO"));
		return onDemandServiceBean;
		}
	}



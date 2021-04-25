package com.josthi.web.dao.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanSelectionForUserBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.utils.Utils;


/*
 * 
 * 	private String planId;
	private String planName;
	private String planDesc;
	private String planPricePerMonthSingle;
	
	private String planActualPriceOneMonthSingle;
	private String planDiscountedPriceOneMonthSingle;
	private String oneMonthSingleSaving;
	

	

	

 */

//TODO Change the percentage : PlanAndPriceDetailToDisplayRowMapper
public class PlanAndPriceDetailToDisplayRowMapper implements RowMapper<PlanSelectionForUserBean> {
	@Override
public PlanSelectionForUserBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		PlanSelectionForUserBean planSelectionForUserBean= new PlanSelectionForUserBean() ;

		planSelectionForUserBean.setPlanId(resultSet.getString("PLAN_TYPE_ID"));
		planSelectionForUserBean.setPlanName(resultSet.getString("PLAN_NAME"));
		planSelectionForUserBean.setPlanDesc(resultSet.getString("DESCRIPTION"));
		
		//Single Month Plan
		String planActualPriceOneMonthSingle = resultSet.getString("ONE_MONTH_ACTUAL");
		String planDiscountedPriceOneMonthSingle = resultSet.getString("ONE_MONTH_DISCOUNT");
		planSelectionForUserBean.setPlanActualPriceOneMonthSingle(Utils.formattedCurrency(planActualPriceOneMonthSingle));
		planSelectionForUserBean.setPlanDiscountedPriceOneMonthSingle(Utils.formattedCurrency(planDiscountedPriceOneMonthSingle));
		planSelectionForUserBean.setOneMonthSingleSaving("0%");
		
		//3 Month Plan
		String planActualPriceThreeMonthSingle =  resultSet.getString("THREE_MONTH_ACTUAL");
		String planDiscountedPriceThreeMonthSingle = resultSet.getString("THREE_MONTH_DISCOUNT");
		planSelectionForUserBean.setPlanActualPriceThreeMonthSingle(Utils.formattedCurrency(planActualPriceThreeMonthSingle));
		planSelectionForUserBean.setPlanDiscountedPriceThreeMonthSingle(Utils.formattedCurrency(planDiscountedPriceThreeMonthSingle));
		planSelectionForUserBean.setThreeMonthSingleSaving("5%");
		
		//6 Month Plan
		String planActualPriceSixMonthSingle = resultSet.getString("SIX_MONTH_ACTUAL");
		String planDiscountedPriceSixMonthSingle = resultSet.getString("SIX_MONTH_DISCOUNT");
		planSelectionForUserBean.setPlanActualPriceSixMonthSingle(Utils.formattedCurrency(planActualPriceSixMonthSingle));
		planSelectionForUserBean.setPlanDiscountedPriceSixMonthSingle(Utils.formattedCurrency(planDiscountedPriceSixMonthSingle));
		planSelectionForUserBean.setSixMonthSingleSaving("10%");
		
		
		//12 Month Plan
		String planActualPriceTwelveMonthSingle = resultSet.getString("TWELVE_MONTH_ACTUAL");
		String planDiscountedPriceTwelveMonthSingle = resultSet.getString("TWELVE_MONTH_DISCOUNT");
		planSelectionForUserBean.setPlanActualPriceTwelveMonthSingle(Utils.formattedCurrency(planActualPriceTwelveMonthSingle));
		planSelectionForUserBean.setPlanDiscountedPriceTwelveMonthSingle(Utils.formattedCurrency(planDiscountedPriceTwelveMonthSingle));
		planSelectionForUserBean.setTwelveMonthSingleSaving("15%");
		


		return planSelectionForUserBean;
		}
	}

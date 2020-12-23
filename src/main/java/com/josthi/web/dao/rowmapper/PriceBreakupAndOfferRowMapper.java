package com.josthi.web.dao.rowmapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.PriceBreakupAndOfferBean;
public class PriceBreakupAndOfferRowMapper implements RowMapper<PriceBreakupAndOfferBean> {
	@Override
public PriceBreakupAndOfferBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		PriceBreakupAndOfferBean priceBreakupAndOfferPO= new PriceBreakupAndOfferBean() ;
		priceBreakupAndOfferPO.setOfferId(resultSet.getInt("OFFER_ID"));
		priceBreakupAndOfferPO.setPlanStartDate(resultSet.getTimestamp("PLAN_START_DATE"));
		priceBreakupAndOfferPO.setPlanEndDate(resultSet.getTimestamp("PLAN_END_DATE"));
		priceBreakupAndOfferPO.setPlanName(resultSet.getString("PLAN_NAME"));
		priceBreakupAndOfferPO.setPlanDurationCode(resultSet.getString("PLAN_DURATION_CODE"));
		priceBreakupAndOfferPO.setPlanBeneficiaryCountCode(resultSet.getString("PLAN_BENEFICIARY_COUNT_CODE"));
		priceBreakupAndOfferPO.setBreakupRequestedBy(resultSet.getString("BREAKUP_REQUESTED_BY"));
		priceBreakupAndOfferPO.setBreakupRequestedFor(resultSet.getString("BREAKUP_REQUESTED_FOR"));
		priceBreakupAndOfferPO.setPlanPricePerPersonPerMonth(resultSet.getString("PLAN_PRICE_PER_PERSON_PER_MONTH"));
		priceBreakupAndOfferPO.setBenefeciciaryCountToDisplay(resultSet.getString("BENEFECICIARY_COUNT_TO_DISPLAY"));
		priceBreakupAndOfferPO.setBasePriceForTotalBenefeciary(resultSet.getString("BASE_PRICE_FOR_TOTAL_BENEFECIARY"));
		priceBreakupAndOfferPO.setDiscountedPriceForTotalBenefeciary(resultSet.getString("DISCOUNTED_PRICE_FOR_TOTAL_BENEFECIARY"));
		priceBreakupAndOfferPO.setPlanDurationToDisplay(resultSet.getString("PLAN_DURATION_TO_DISPLAY"));
		priceBreakupAndOfferPO.setDiscountPercentageForFamilyPlan(resultSet.getString("DISCOUNT_PERCENTAGE_FOR_FAMILY_PLAN"));
		priceBreakupAndOfferPO.setBasePriceForSelectedDuration(resultSet.getString("BASE_PRICE_FOR_SELECTED_DURATION"));
		priceBreakupAndOfferPO.setDiscountedPriceForSelectedDuration(resultSet.getString("DISCOUNTED_PRICE_FOR_SELECTED_DURATION"));
		priceBreakupAndOfferPO.setFinalDiscountedPrice(resultSet.getString("FINAL_DISCOUNTED_PRICE"));
		priceBreakupAndOfferPO.setTotalGain(resultSet.getString("TOTAL_GAIN"));
		priceBreakupAndOfferPO.setBreakupCreatedOn(resultSet.getTimestamp("BREAKUP_CREATED_ON"));
		return priceBreakupAndOfferPO;
		}
	}


//SELECT OFFER_ID, PLAN_START_DATE, PLAN_END_DATE, PLAN_NAME, PLAN_DURATION_CODE, PLAN_BENEFICIARY_COUNT_CODE, BREAKUP_REQUESTED_BY, 
//BREAKUP_REQUESTED_FOR, PLAN_PRICE_PER_PERSON_PER_MONTH, BENEFECICIARY_COUNT_TO_DISPLAY, BASE_PRICE_FOR_TOTAL_BENEFECIARY, DISCOUNTED_PRICE_FOR_TOTAL_BENEFECIARY, 
//PLAN_DURATION_TO_DISPLAY, DISCOUNT_PERCENTAGE_FOR_FAMILY_PLAN, BASE_PRICE_FOR_SELECTED_DURATION, DISCOUNTED_PRICE_FOR_SELECTED_DURATION, FINAL_DISCOUNTED_PRICE, TOTAL_GAIN, BREAKUP_CREATED_ON, IS_PLAN_PURCHASED
//FROM price_breakup_offer where OFFER_ID = ?;
package com.josthi.web.dao.rowmapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.PriceBreakupAndOfferBean;
import com.josthi.web.bo.PurchasedPlanToDisplay;
import com.josthi.web.utils.Utils;
public class PurchasedPlanToDisplayRowMapper implements RowMapper<PurchasedPlanToDisplay> {
	@Override
public PurchasedPlanToDisplay mapRow(ResultSet resultSet,int arg1)throws SQLException {
		PurchasedPlanToDisplay purchasedPlanToDisplay= new PurchasedPlanToDisplay() ;
		purchasedPlanToDisplay.setPlanStartDate(resultSet.getTimestamp("PLAN_START_DATE"));
		purchasedPlanToDisplay.setPlanStartDateStr(Utils.timestampToFormattedString(resultSet.getTimestamp("PLAN_START_DATE"), "d MMM yyyy"));
		
		purchasedPlanToDisplay.setPlanEndDate(resultSet.getTimestamp("PLAN_END_DATE"));
		purchasedPlanToDisplay.setPlanEndDateStr(Utils.timestampToFormattedString(resultSet.getTimestamp("PLAN_END_DATE"), "d MMM yyyy"));
		
		purchasedPlanToDisplay.setPlanName(resultSet.getString("PLAN_NAME"));
		purchasedPlanToDisplay.setPlanDurationCode(Utils.getDayDiff(resultSet.getTimestamp("PLAN_START_DATE"), resultSet.getTimestamp("PLAN_END_DATE")) + "Days Plan");
		purchasedPlanToDisplay.setBreakupRequestedFor(resultSet.getString("BREAKUP_REQUESTED_FOR"));
		purchasedPlanToDisplay.setFinalDiscountedPrice(resultSet.getString("FINAL_DISCOUNTED_PRICE"));
		
		if(Utils.isPlanExpired(resultSet.getTimestamp("PLAN_END_DATE"))) {
			purchasedPlanToDisplay.setPlanComments("Plan already Expired. The service is terminated.");
			purchasedPlanToDisplay.setPlanActive("N");
		}else {
			purchasedPlanToDisplay.setPlanActive("Y");
			purchasedPlanToDisplay.setPlanComments("Plan is still valid for "+Utils.getDaysRemainingForPlanToExpire(resultSet.getTimestamp("PLAN_END_DATE"))+" days.");
		}
		return purchasedPlanToDisplay;
		}
	}

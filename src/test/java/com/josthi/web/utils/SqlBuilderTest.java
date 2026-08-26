package com.josthi.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.josthi.web.bo.ServiceRequestBean;

class SqlBuilderTest {

	private static ServiceRequestBean emptyBean() {
		ServiceRequestBean bean = new ServiceRequestBean();
		bean.setTicketNo("");
		bean.setRequestedBy("");
		bean.setRequesterId("");
		bean.setRequestedFor("");
		bean.setBeneficiaryId("");
		bean.setAssignedTo("");
		bean.setServiceType("");
		bean.setServiceCategory("");
		bean.setServiceStatus("");
		bean.setLastUpdatedBy("");
		return bean;
	}

	@Test
	void singleCriterionProducesWhereClauseWithoutTrailingAnd() {
		ServiceRequestBean bean = emptyBean();
		bean.setTicketNo("TKT2009110001");

		assertEquals("where TICKET_NO='TKT2009110001' ", SqlBuilder.getTicketSearchCondition(bean));
	}

	@Test
	void inputIsTrimmedBeforeBeingUsed() {
		ServiceRequestBean bean = emptyBean();
		bean.setRequesterId("  RU2009110001  ");

		assertEquals("where REQUESTER_ID='RU2009110001' ", SqlBuilder.getTicketSearchCondition(bean));
	}

	@Test
	void multipleCriteriaAreJoinedWithAnd() {
		ServiceRequestBean bean = emptyBean();
		bean.setTicketNo("TKT1");
		bean.setAssignedTo("AG1");
		bean.setServiceStatus("OPEN");

		assertEquals("where TICKET_NO='TKT1' AND ASSIGNED_TO='AG1' AND SERVICE_STATUS='OPEN' ",
				SqlBuilder.getTicketSearchCondition(bean));
	}

	@Test
	void everySupportedColumnIsMapped() {
		ServiceRequestBean bean = emptyBean();
		bean.setTicketNo("a");
		bean.setRequestedBy("b");
		bean.setRequesterId("c");
		bean.setRequestedFor("d");
		bean.setBeneficiaryId("e");
		bean.setAssignedTo("f");
		bean.setServiceType("g");
		bean.setServiceCategory("h");
		bean.setServiceStatus("i");
		bean.setLastUpdatedBy("j");

		String condition = SqlBuilder.getTicketSearchCondition(bean);

		assertEquals("where TICKET_NO='a' AND REQUESTED_BY='b' AND REQUESTER_ID='c' AND REQUESTED_FOR='d' "
				+ "AND BENEFICIARY_ID='e' AND ASSIGNED_TO='f' AND SERVICE_TYPE='g' AND SERVICE_CATEGORY='h' "
				+ "AND SERVICE_STATUS='i' AND LAST_UPDATE_USER='j' ", condition);
	}

	@Test
	void noCriteriaProducesNoWhereClause() {
		String condition = SqlBuilder.getTicketSearchCondition(emptyBean());

		assertEquals(" ", condition);
		assertFalse(condition.contains("AND"));
		assertTrue(condition.isBlank());
	}
}

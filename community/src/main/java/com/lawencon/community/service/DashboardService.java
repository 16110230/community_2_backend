package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.constant.ActivityType;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityInvoiceDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.pojo.dashboard.PojoDashboard;
import com.lawencon.community.pojo.dashboard.ShowDashboard;

@Service
public class DashboardService {

	@Autowired
	private UsersDao userDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ActivityTypeDao activityTypeDao;
	
	@Autowired
	private ActivityInvoiceDao activityInvoiceDao;
	
	@Autowired
	private SubscriptionDao subscriptionDao;
	
	public ShowDashboard countData() {
		ShowDashboard response = new ShowDashboard();
		Long usersDaily = userDao.countAllByToday();
		Long users = userDao.countAll();
		
		String activityTypeEventId = activityTypeDao.getByCode(ActivityType.EVENT.getCode());
		String activityTypeCourseId = activityTypeDao.getByCode(ActivityType.COURSE.getCode());
		
		Long activityEvent = activityDao.countAllByToday(activityTypeEventId);
		Long activityCourse = activityDao.countAllByToday(activityTypeCourseId);
		
		Long invoicePendingEvent = activityInvoiceDao.countAllInvoicePendingByActivityType(ActivityType.EVENT.getCode());
		Long invoicePendingCourse = activityInvoiceDao.countAllInvoicePendingByActivityType(ActivityType.COURSE.getCode());
				
		Long invoicePendingSubscription = subscriptionDao.countAllInvoicePending();
		
		PojoDashboard result = new PojoDashboard();
		result.setCountDailyUsers(usersDaily);
		result.setCountUsers(users);
		result.setCountEvent(activityEvent);
		result.setCountCourse(activityCourse);
		result.setCountPendingCourseInvoice(invoicePendingCourse);
		result.setCountPendingEventInvoice(invoicePendingEvent);
		result.setCountPendingSubscribeInvoice(invoicePendingSubscription);
		
		response.setData(result);
		
		return response;
	}
}

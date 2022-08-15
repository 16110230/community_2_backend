package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.constant.ActivityTypeCategory;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityInvoiceDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.ThreadDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.pojo.dashboard.PojoDashboard;
import com.lawencon.community.pojo.dashboard.ShowDashboard;
import com.lawencon.community.pojo.thread.PojoThread;
import com.lawencon.community.pojo.thread.ShowThreads;



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
	
	@Autowired
	private ThreadDao threadDao;
	
	public ShowDashboard countData() throws Exception  {
		ShowDashboard response = new ShowDashboard();
		Long usersDaily = userDao.countAllByToday();
		Long users = userDao.countAll();
		
		String activityTypeEventId = activityTypeDao.getByCode(ActivityTypeCategory.EVENT.name());
		String activityTypeCourseId = activityTypeDao.getByCode(ActivityTypeCategory.COURSE.name());
		
		Long activityEvent = activityDao.countAllByToday(activityTypeEventId);
		Long activityCourse = activityDao.countAllByToday(activityTypeCourseId);
		
		Long invoicePendingEvent = activityInvoiceDao.countAllInvoicePendingByActivityType(ActivityTypeCategory.EVENT.getCode());
		Long invoicePendingCourse = activityInvoiceDao.countAllInvoicePendingByActivityType(ActivityTypeCategory.COURSE.getCode());
				
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
	
	public ShowThreads showThreadByNewest() throws Exception {
		ShowThreads response = new ShowThreads();
		
		List<PojoThread> thread = threadDao.getNewestThread();
		
		response.setData(thread);

		return response;
	}
	
}

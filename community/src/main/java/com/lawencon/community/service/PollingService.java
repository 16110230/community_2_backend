package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PollingDao;
import com.lawencon.community.dao.PollingDetailsDao;
import com.lawencon.community.dao.ThreadDao;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingDetails;
import com.lawencon.community.model.Thread;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.polling.InsertPollingDtlReq;
import com.lawencon.community.pojo.polling.InsertPollingMainReq;
import com.lawencon.community.pojo.polling.PojoPolling;
import com.lawencon.community.pojo.polling.ShowPollingById;
import com.lawencon.model.SearchQuery;

@Service
public class PollingService extends BaseCoreService<Polling> {
	@Autowired
	private PollingDao pollingDao;

	@Autowired
	private PollingDetailsDao detailsDao;

	@Autowired
	private ThreadDao threadDao;

	public SearchQuery<PojoPolling> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Polling> result = pollingDao.findAll(query, startPage, maxPage);
		List<PojoPolling> pollings = new ArrayList<PojoPolling>();

		result.getData().forEach(val -> {
			PojoPolling polling = new PojoPolling();
			Thread thread = threadDao.getById(val.getThread().getId());

			polling.setId(val.getId());
			polling.setThread(thread.getId());
			polling.setThreadTitle(thread.getThreadTitle());
			polling.setIsActive(val.getIsActive());
			polling.setVersion(val.getVersion());

			pollings.add(polling);
		});

		SearchQuery<PojoPolling> response = new SearchQuery<PojoPolling>();
		response.setData(pollings);
		response.setCount(result.getCount());

		return response;
	}

	public ShowPollingById showById(String id) throws Exception {
		Polling polling = pollingDao.getById(id);
		PojoPolling data = new PojoPolling();
		ShowPollingById response = new ShowPollingById();
		Thread thread = threadDao.getById(polling.getThread().getId());

		data.setId(polling.getId());
		data.setThread(thread.getId());
		data.setThreadTitle(thread.getThreadTitle());
		data.setIsActive(polling.getIsActive());
		data.setVersion(polling.getVersion());

		response.setData(data);

		return response;
	}

	public PojoInsertRes insert(InsertPollingMainReq data, Thread thread) throws Exception {
		Polling insert = new Polling();
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();

		insert.setThread(thread);
		insert.setIsActive(data.getHeader().getIsActive());

		Polling result = pollingDao.saveNew(insert);

		for (InsertPollingDtlReq val : data.getDetails()) {
			PollingDetails detail = new PollingDetails();
			detail.setPolling(result);
			detail.setPollingDetailsName(val.getPollingDetailsName());
			detail.setIsActive(val.getIsActive());
			detail.setCreatedBy(result.getCreatedBy());

			detailsDao.saveNew(detail);
		}
		
		resData.setId(result.getId());
		response.setMessage("Successfully add new data!");
		response.setData(resData);

		return response;
	}
}

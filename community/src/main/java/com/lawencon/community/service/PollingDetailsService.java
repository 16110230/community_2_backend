package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PollingDetailsDao;
import com.lawencon.community.model.PollingDetails;
import com.lawencon.community.pojo.polling.PojoPollingDetails;
import com.lawencon.community.pojo.polling.ShowPollingDetails;

@Service
public class PollingDetailsService extends BaseCoreService<PollingDetails> {
	@Autowired
	private PollingDetailsDao detailsDao;
	
	public ShowPollingDetails getAllByPolling(String id) throws Exception {
		ShowPollingDetails response = new ShowPollingDetails();
		
		List<PollingDetails> details = detailsDao.findAllByPolling(id);
		List<PojoPollingDetails> pojoDetails = new ArrayList<PojoPollingDetails>();
		
		details.forEach(val -> {
			PojoPollingDetails detail = new PojoPollingDetails();
			detail.setId(val.getId());
			detail.setPolling(val.getPolling().getId());
			detail.setPollingDetailsName(val.getPollingDetailsName());
			detail.setIsActive(val.getIsActive());
			detail.setVersion(val.getVersion());
			
			pojoDetails.add(detail);
		});
		
		response.setData(pojoDetails);
		
		return response;
	}
}

package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.pojo.position.PojoPosition;
import com.lawencon.model.SearchQuery;

public class PositionService extends BaseCoreService<Position> {
	@Autowired
	private PositionDao positionDao;
	
	public SearchQuery<PojoPosition> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Position> positions = positionDao.findAll(query, startPage, maxPage);
		List<PojoPosition> result = new ArrayList<PojoPosition>();
		
		positions.getData().forEach(val -> {
			PojoPosition data = new PojoPosition();
			
			data.setId(val.getId());
			data.setPositionName(val.getPositionName());
			data.setPositionCode(val.getPositionCode());
			data.setIsActive(val.getIsActive());
			data.setVersion(val.getVersion());
			
			result.add(data);
		});
		
		SearchQuery<PojoPosition> response = new SearchQuery<PojoPosition>();
		response.setData(result);
		response.setCount(positions.getCount());
		
		return response;
	}
}

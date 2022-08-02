package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.ThreadCategoryType;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PollingDao;
import com.lawencon.community.dao.PollingDetailsDao;
import com.lawencon.community.dao.ThreadActivityDao;
import com.lawencon.community.dao.ThreadCategoryDao;
import com.lawencon.community.dao.ThreadDao;
import com.lawencon.community.dao.ThreadDetailsDao;
import com.lawencon.community.dao.UserPollingDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingDetails;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadCategory;
import com.lawencon.community.model.UserPolling;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.thread.InsertThreadReq;
import com.lawencon.community.pojo.thread.PojoThread;
import com.lawencon.community.pojo.thread.ShowThreadById;
import com.lawencon.community.pojo.thread.ShowThreads;
import com.lawencon.community.pojo.thread.UpdateThreadReq;
import com.lawencon.model.SearchQuery;

@Service
public class ThreadService extends BaseService<Thread>{

	@Autowired
	private ThreadDao threadDao;

	@Autowired
	private ThreadCategoryDao threadCategoryDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private PollingService pollingService;
	
	@Autowired
	private ThreadActivityDao threadActivityDao;
	
	@Autowired
	private PollingDao pollingDao;
	
	@Autowired
	private PollingDetailsDao pollingDetailsDao;
	
	@Autowired
	private UserPollingDao userPollingDao;
	
	@Autowired
	private ThreadDetailsDao threadDetailsDao;

	public SearchQuery<PojoThread> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Thread> threads = threadDao.findAll(query, startPage, maxPage, "threadTitle", "threadContent");
		List<PojoThread> result = new ArrayList<PojoThread>();

		threads.getData().forEach(val -> {
			PojoThread thread = new PojoThread();
			ThreadCategory threadCategory = threadCategoryDao.getById(val.getThreadCategory().getId());
			Users user = usersDao.getById(val.getUser().getId());

			if (val.getFile() != null) {
				File file = fileDao.getById(val.getFile().getId());
				thread.setFile(file.getId());				
			}

			thread.setId(val.getId());
			thread.setThreadTitle(val.getThreadTitle());
			thread.setThreadContent(val.getThreadContent());
			thread.setIsLike(threadDao.isLike(getUserId(), val.getId()));
			thread.setIsBookmark(threadDao.isBookmark(getUserId(), val.getId()));
			thread.setCountBookmark(threadDao.countBookmark(val.getId()));
			thread.setCountLike(threadDao.countLike(val.getId()));
			thread.setCountComment(threadDao.countComment(val.getId()));
			
			thread.setUser(user.getId());
			thread.setUserName(user.getUsername());
			thread.setThreadcategory(threadCategory.getId());
			thread.setThreadCategoryName(threadCategory.getCategoryName());
			thread.setCreatedAt(val.getCreatedAt());
			thread.setIsActive(val.getIsActive());
			thread.setVersion(val.getVersion());

			result.add(thread);
		});

		SearchQuery<PojoThread> response = new SearchQuery<PojoThread>();
		response.setData(result);
		response.setCount(threads.getCount());

		return response;
	}

	public ShowThreadById showById(String id) {
		Thread threads = threadDao.getById(id);
		PojoThread thread = new PojoThread();

		ThreadCategory threadCategory = threadCategoryDao.getById(threads.getThreadCategory().getId());
		Users user = usersDao.getById(threads.getUser().getId());
		File file = fileDao.getById(threads.getFile().getId());

		thread.setId(threads.getId());
		thread.setThreadTitle(threads.getThreadTitle());
		thread.setThreadContent(threads.getThreadContent());
		thread.setFile(file.getId());
		thread.setUser(user.getId());
		thread.setUserName(user.getUsername());
		thread.setThreadcategory(threadCategory.getId());
		thread.setThreadCategoryName(threadCategory.getCategoryName());
		thread.setCreatedAt(threads.getCreatedAt());
		thread.setIsActive(threads.getIsActive());
		thread.setVersion(threads.getVersion());
		thread.setIsLike(threadDao.isLike(getUserId(), threads.getId()));
		thread.setIsBookmark(threadDao.isBookmark(getUserId(), threads.getId()));
		thread.setCountBookmark(threadDao.countBookmark(threads.getId()));
		thread.setCountLike(threadDao.countLike(threads.getId()));
		thread.setCountComment(threadDao.countComment(threads.getId()));

		ShowThreadById response = new ShowThreadById();
		response.setData(thread);

		return response;
	}

	public PojoInsertRes insert(InsertThreadReq data) throws Exception {
		Thread insert = new Thread();
		ThreadCategory threadCategory = threadCategoryDao.getById(data.getThreadCategory());
		Users user = usersDao.getById(getUserId());

		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		insert.setThreadTitle(data.getThreadTitle());
		insert.setThreadContent(data.getThreadContent());
		insert.setThreadCategory(threadCategory);
		insert.setIsActive(true);
		insert.setUser(user);

		try {
			begin();			
			if (data.getFileName() != null) {
				File file = new File();
				file.setFileName(data.getFileName());
				file.setFileExt(data.getFileExt());
				file.setIsActive(data.getIsActive());
				File result = fileDao.saveNew(file);		
				insert.setFile(result);
			}

			Thread result = threadDao.saveNew(insert);
			commit();

			if (data.getPolling() != null) {
				pollingService.insert(data.getPolling(), result.getId());
			}

			resData.setId(result.getId());
			resData.setMessage("Successfully insert new data!");
			response.setData(resData);

		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}

	public PojoUpdateRes update(UpdateThreadReq data) throws Exception {
		Thread update = threadDao.getById(data.getId());
		ThreadCategory threadCategory = threadCategoryDao.getById(data.getThreadCategory());

		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setThreadTitle(data.getThreadTitle());
		update.setThreadContent(data.getThreadContent());
		update.setThreadCategory(threadCategory);
		update.setIsActive(data.getIsActive());
		update.setVersion(data.getVersion());

		try {
			begin();

			Thread result = threadDao.save(update);
			resData.setVersion(result.getVersion());
			resData.setMessage("Successfully update the data!");
			response.setData(resData);

			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}

	public ShowThreads getArticles() throws Exception {
		ShowThreads response = new ShowThreads();

		List<Thread> thread = threadDao.getThreadArticle();
		List<PojoThread> pojoThread = new ArrayList<PojoThread>();

		thread.forEach(val -> {
			PojoThread detail = new PojoThread();
			ThreadCategory cat = threadCategoryDao.getById(val.getThreadCategory().getId());
			Users user = usersDao.getById(val.getUser().getId());

			detail.setId(val.getId());
			detail.setThreadTitle(val.getThreadTitle());
			detail.setThreadContent(val.getThreadContent());
			detail.setThreadcategory(val.getThreadCategory().getId());
			detail.setThreadCategoryName(cat.getCategoryName());
			detail.setFile(val.getFile().getId());
			detail.setUserName(user.getUsername());
			detail.setCreatedAt(val.getCreatedAt());

			pojoThread.add(detail);
		});

		response.setData(pojoThread);

		return response;
	}
	
	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteRes response = new PojoDeleteRes();
		Thread thread = threadDao.getById(id);
		
		if (ThreadCategoryType.POL.name() == thread.getThreadCategory().getCategoryCode()) {
			Polling polling = pollingDao.getByThreadId(id);
			List<PollingDetails> pollingDetail = pollingDetailsDao.findAllByPolling(polling.getId());
			
			int sizePolling = pollingDetail.size();
			
			try {
				boolean deletePollDetail = false;
				
				for (int i = 0; i < sizePolling; i++) {
					UserPolling userPolling = userPollingDao.getById(pollingDetail.get(i).getPolling().getId());
					boolean deleteUserPolling = userPollingDao.deleteById(userPolling.getId());
					if (deleteUserPolling) {
						deletePollDetail = pollingDao.deleteById(pollingDetail.get(i).getId());					
					}
				}
				
				if (deletePollDetail) {
					boolean result = threadDao.deleteById(id);
					if (result) {
						response.setMessage("Successfully delete the data!");
					}
				} else {
					boolean result = threadDao.deleteById(id);
					if (result) {
						response.setMessage("Successfully delete the data!");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				throw new Exception(e);
			}
		} else if (ThreadCategoryType.ART.name().equalsIgnoreCase(thread.getThreadCategory().getCategoryCode())) {
			try {
				begin();
				
				boolean result = threadDao.deleteById(id);
				if (result) {
					response.setMessage("Successfully delete the data!");
				}
				
				commit();
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				throw new Exception(e);
			}
			
		} else {
			try {
				begin();
				
				boolean deleteThreadAct = threadActivityDao.deleteByThreadId(id);
				
				if (deleteThreadAct) {
					boolean deleteThreadDetail = threadDetailsDao.deleteByThreadId(id);
					if (deleteThreadDetail) {
						boolean result = threadDao.deleteById(id);
						if (result) {
							response.setMessage("Successfully delete the data!");
						}
					}
				} else {
					boolean deleteThreadDetail = threadDetailsDao.deleteByThreadId(id);
					if (deleteThreadDetail) {
						boolean result = threadDao.deleteById(id);
						if (result) {
							response.setMessage("Successfully delete the data!");
						}
					} else {
						boolean result = threadDao.deleteById(id);
						if (result) {
							response.setMessage("Successfully delete the data!");
						}
					}
				}
				
				commit();
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				throw new Exception(e);
			}
			
		}

		return response;
	}
	
	public ShowThreads showThreadForUser() {
		ShowThreads response = new ShowThreads();

		List<PojoThread> thread = threadDao.getThreadForUser(getUserId());
		
		response.setData(thread);

		return response;
	}

	public PojoInsertRes insertArticle(InsertThreadReq data) throws Exception {
		Thread insert = new Thread();
		
		ThreadCategory threadCategory = threadCategoryDao.getCategoryCode(ThreadCategoryType.ART.name());
		Users user = usersDao.getById(getUserId());

		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		if (data.getFileName() != null) {
			File file = new File();
			file.setFileName(data.getFileName());
			file.setFileExt(data.getFileExt());
			file.setIsActive(data.getIsActive());
			File fileResult = fileDao.save(file);		
			
			insert.setFile(fileResult);
		}

		insert.setThreadTitle(data.getThreadTitle());
		insert.setThreadContent(data.getThreadContent());
		insert.setThreadCategory(threadCategory);
		insert.setIsActive(true);
		insert.setUser(user);

		try {
			begin();

			Thread result = threadDao.saveNew(insert);

			if (data.getPolling() != null) {
				pollingService.insert(data.getPolling(), result.getId());
			}

			resData.setId(result.getId());
			resData.setMessage("Successfully insert new data!");
			response.setData(resData);

			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}
	
	public SearchQuery<PojoThread> showAllArticles(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Thread> threads = threadDao.findAll(query, startPage, maxPage, "threadTitle", "threadContent");
		List<PojoThread> result = new ArrayList<PojoThread>();

		threads.getData().forEach(val -> {
			if(val.getThreadCategory().getCategoryCode().equalsIgnoreCase(ThreadCategoryType.ART.name())) {
				PojoThread thread = new PojoThread();
				ThreadCategory threadCategory = threadCategoryDao.getById(val.getThreadCategory().getId());
				Users user = usersDao.getById(val.getUser().getId());
				
				if (val.getFile() != null) {
					File file = fileDao.getById(val.getFile().getId());
					thread.setFile(file.getId());				
				}
				
				thread.setId(val.getId());
				thread.setThreadTitle(val.getThreadTitle());
				thread.setThreadContent(val.getThreadContent());
				thread.setIsLike(threadDao.isLike(getUserId(), val.getId()));
				thread.setIsBookmark(threadDao.isBookmark(getUserId(), val.getId()));
				thread.setCountBookmark(threadDao.countBookmark(val.getId()));
				thread.setCountLike(threadDao.countLike(val.getId()));
				thread.setCountComment(threadDao.countComment(val.getId()));
				
				thread.setUser(user.getId());
				thread.setUserName(user.getUsername());
				thread.setThreadcategory(threadCategory.getId());
				thread.setThreadCategoryName(threadCategory.getCategoryName());
				thread.setCreatedAt(val.getCreatedAt());
				thread.setIsActive(val.getIsActive());
				thread.setVersion(val.getVersion());
				
				result.add(thread);
			}
		});

		SearchQuery<PojoThread> response = new SearchQuery<PojoThread>();
		response.setData(result);
		response.setCount(threads.getCount());

		return response;
	}
}

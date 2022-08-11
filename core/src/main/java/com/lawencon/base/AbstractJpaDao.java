package com.lawencon.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.search.engine.search.query.SearchFetchable;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

import com.lawencon.model.SearchQuery;
import com.lawencon.security.PrincipalService;

/**
 * 
 * @author Agung Damas Saputra
 * 
 */
@Repository
public class AbstractJpaDao<T extends BaseEntity> {

	public Class<T> clazz;
	
	@Autowired
	private PrincipalService principalService;

	@SuppressWarnings("unchecked")
	public AbstractJpaDao() {
		this.clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractJpaDao.class);
	}

	public T getById(final String id) {
		T data = em().find(clazz, id);
		em().detach(data);
		return data;
	}

	public T getByIdWithoutDetach(final String id) {
		T data = em().find(clazz, id);
		return data;
	}

	public List<T> getAll() {
		return em().createQuery("FROM " + clazz.getName(), clazz)
				.getResultList();
	}

	public Long countAll() {
		return (Long) em().createQuery("SELECT COUNT(id) FROM " + clazz.getName())
				.getSingleResult();
	}
	
	public Long countAll(String whereClause, 
			String[] parameterNames, String[] parameterValues) {
		Query query = em().createQuery(
				"SELECT COUNT(*) FROM " + clazz.getName() + (whereClause != null ? " " + whereClause : ""));

		if (parameterNames != null && parameterValues != null) {
			for (int i = 0; i < parameterValues.length; i++) {
				query.setParameter(parameterNames[i], parameterValues[i]);
			}
		}

		return (Long) query.getSingleResult();
	}


	public List<T> getAll(int startPage, int maxPage) {
		return em().createQuery("FROM " + clazz.getName(), clazz)
				.setFirstResult(startPage)
				.setMaxResults(maxPage)
				.getResultList();
	}
	
	public List<T> getAll(String whereClause, 
			String[] parameterNames, String[] parameterValues, 
			Integer startPosition, Integer limit) {
		TypedQuery<T> typedQuery = em().createQuery(
				"FROM " + clazz.getName() + (whereClause != null ? " " + whereClause : ""), clazz);

		if (parameterNames != null && parameterValues != null) {
			for (int i = 0; i < parameterValues.length; i++) {
				typedQuery.setParameter(parameterNames[i], parameterValues[i]);
			}
		}

		if (startPosition != null)
			typedQuery.setFirstResult(startPosition);
		if (limit != null)
			typedQuery.setMaxResults(limit);

		return typedQuery.getResultList();
	}
	
	public SearchQuery<T> searchQuery(String whereClause, String[] parameterNames, String[] parameterValues, 
			Integer startPosition, Integer limit) {
		List<T> resultData = getAll(whereClause, parameterNames, parameterValues, startPosition, limit);
		Integer countData = countAll(whereClause, parameterNames, parameterValues).intValue();
		
		SearchQuery<T> data = new SearchQuery<>();
		data.setData(resultData);
		data.setCount(countData);

		return data;
	}

	public SearchQuery<T> searchQuery(String textQuery, 
			int startPosition, int limit,
			String orderBy,
			String... fields) {

		String[] querySplit = extractQuery(textQuery);
		
		StringBuilder whereCriteriaBuilder = new StringBuilder("");
		
		for (String field : fields) {
			String fieldTrim = field.trim();
			for (String subQuery : querySplit) {				
				whereCriteriaBuilder.append("OR LOWER")
					.append("(")
					.append(fieldTrim)
					.append(") ")
					.append("LIKE CONCAT('%'")
					.append(",")
					.append("'")
					.append(subQuery.toLowerCase())
					.append("'")
					.append(",")
					.append("'%')");
			}
		}
		
		String whereCriteria = whereCriteriaBuilder.toString().replaceFirst("OR", "");
			
		Long resultCount = (Long) em()
				.createQuery("SELECT COUNT(*) FROM " + clazz.getName() + (whereCriteria != null ? " WHERE " + whereCriteria : ""))
				.getSingleResult(); 
		
		List<T> resultData = em().createQuery("FROM " + clazz.getName() + (whereCriteria != null ? " WHERE " + whereCriteria + orderBy: ""), clazz)
				.setFirstResult(startPosition)
				.setMaxResults(limit)
				.getResultList();
		
		SearchQuery<T> data = new SearchQuery<>();
		data.setData(resultData);
		data.setCount(resultCount.intValue());

		return data;
	}


	
	public SearchQuery<T> findAll(String textQuery, 
			Integer startPosition, Integer limit,
			String... fields) throws Exception {
		SearchQuery<T> sq = new SearchQuery<>();
		List<T> data = null;

		if (startPosition == null || limit == null) {
			data = getAll();
			sq.setData(data);
		} else {
			if (textQuery == null) {
				data = getAll(startPosition, limit);
				Integer count = countAll().intValue();

				sq.setData(data);
				sq.setCount(count);
			} else {
				return searchQuery(textQuery, startPosition, limit,null, fields);
			}
		}

		return sq;
	}
	
	public SearchQuery<T> findAllAndOrder(String textQuery, 
			Integer startPosition, Integer limit,
			String orderBy,
			String... fields) throws Exception {
		SearchQuery<T> sq = new SearchQuery<>();
		List<T> data = null;

		if (startPosition == null || limit == null) {
			data = getAll();
			sq.setData(data);
		} else {
			if (textQuery == null) {
				data = getAll(startPosition, limit);
				Integer count = countAll().intValue();

				sq.setData(data);
				sq.setCount(count);
			} else {
				return searchQuery(textQuery, startPosition, limit, orderBy, fields);
			}
		}

		return sq;
	}


	public T save(T entity) throws Exception {
		if (entity.getId() != null) {
			entity = em().merge(entity);
			em().flush();
		} else {
			em().persist(entity);
		}

		return entity;
	}
	
	public T saveNew(T entity) throws Exception {
		if (entity.getId() != null) {
			entity.setUpdatedBy(principalService.getPrincipal());
			entity = em().merge(entity);
			em().flush();
		} else {
			entity.setCreatedBy(principalService.getPrincipal());
			em().persist(entity);
		}

		return entity;
	}
	
	public T saveNonLogin(T entity, Supplier<String> getIdFunc) throws Exception {
		if (entity.getId() != null) {
			entity.setUpdatedBy(getIdFunc.get());
			entity = em().merge(entity);
			em().flush();
		} else {
			entity.setCreatedBy(getIdFunc.get());
			em().persist(entity);
		}

		return entity;
	}

	public void delete(final T entity) throws Exception {
		em().remove(entity);
	}

	public boolean deleteById(final Object entityId) throws Exception {
		T entity = null;
		if (entityId != null && entityId instanceof String) {
			entity = em().find(clazz, (String) entityId);
		}

		if (entity != null) {
			delete(entity);
			return true;
		}

		return false;
	}

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	protected <C> TypedQuery<C> createQuery(String sql, Class<C> clazz) {
		return em().createQuery(sql, clazz);
	}

	protected Query createNativeQuery(String sql) {
		return em().createNativeQuery(sql);
	}

	private String[] extractQuery(String textQuery) {
		Pattern pattern = Pattern.compile("\\s{2,}");
		Matcher matcher = pattern.matcher(textQuery);
		
		String textQueryFinal = matcher.replaceAll(" ").trim();
		String[] result = textQueryFinal.split(" ");
		
		return result;
	}

}

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

	public List<T> getAll(int startPage, int maxPage) {
		return em().createQuery("FROM " + clazz.getName(), clazz)
				.setFirstResult(startPage)
				.setMaxResults(maxPage)
				.getResultList();
	}

	private SearchQuery<T> getAll(String query, int startPage, int maxPage, String... fields) {
		SearchFetchable<T> searchObj = Search.session(ConnHandler.getManager())
				.search(clazz)
				.where(f -> f.wildcard().fields(fields).matching("*" + query + "*"));

		List<T> result = searchObj.fetch(startPage, maxPage).hits();
		List<T> resultAll = searchObj.fetchAllHits();

		SearchQuery<T> data = new SearchQuery<>();
		data.setData(result);
		data.setCount(resultAll.size());

		return data;
	}

	public SearchQuery<T> searchQuery(String textQuery, 
			int startPosition, int limit,
			String... fields) {

		EntityManager em = em();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQueryData = criteriaBuilder.createQuery(clazz);

		Root<T> rootData = criteriaQueryData.from(clazz);

		String[] querySplit = extractQuery(textQuery);

		List<Predicate> predicates = new ArrayList<>();

		Map<String, Join<Object, Object>> joinMap = new HashMap<>();
		
		for (String field : fields) {
			String fieldTrim = field.trim();
			if (fieldTrim.contains(".")) {
				doJoin(criteriaBuilder, rootData, fieldTrim, querySplit, predicates, joinMap);
			} else {
				for (String subQuery : querySplit) {
					Predicate condition = criteriaBuilder.like(
							criteriaBuilder.lower(rootData.get(field)), "%" + subQuery.toLowerCase() + "%"
					);
					predicates.add(condition);
				}
			}
		}

		Predicate[] predicateArr = new Predicate[predicates.size()];
		for (int i = 0; i < predicates.size(); i++) {
			predicateArr[i] = predicates.get(i);
		}
		Predicate predicate = criteriaBuilder.or(predicateArr);

		//TODO : query count not yet works :(
		//criteriaQueryCount
		//	.select(criteriaBuilderCount.count(rootCount))
		//	.where(predicate);

		//Long resultCount = em().createQuery(criteriaQueryCount)
		//		.getSingleResult();
		
		criteriaQueryData
			.where(predicate)
			.orderBy(criteriaBuilder.asc(rootData.get("createdAt")));


		List<T> resultData = em().createQuery(criteriaQueryData)
				.setFirstResult(startPosition)
				.setMaxResults(limit)
				.getResultList();

		int resultCount = em().createQuery(criteriaQueryData)
				.getResultList()
				.size();

		SearchQuery<T> data = new SearchQuery<>();
		data.setData(resultData);
		data.setCount(resultCount);

		return data;
	}
	
	private <T> void doJoin(CriteriaBuilder criteriaBuilder, 
			Root<T> rootData, 
			String field, String[] extractQuery,
			List<Predicate> predicates,
			Map<String, Join<Object, Object>> joinMap) {
		
		Join<Object, Object> joinData = null;
		
		String[] fieldSplit = field.split("\\.");

		for (int i = 0; i < fieldSplit.length - 1; i++) {
			if (!joinMap.containsKey(fieldSplit[i])) {
				if (i == 0) {
					joinData = rootData.join(fieldSplit[i]);
				} else {
					joinData = joinData.join(fieldSplit[i]);
				}
				joinMap.put(fieldSplit[i], joinData);
			} else {
				joinData = joinMap.get(fieldSplit[i]);
			}
		}
		
		for (String subQuery : extractQuery) {
			Predicate condition = criteriaBuilder.like(
					criteriaBuilder.lower(joinData.get(fieldSplit[fieldSplit.length - 1])), "%" + subQuery.toLowerCase() + "%"
			);
			predicates.add(condition);
		}
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
				return searchQuery(textQuery, startPosition, limit, fields);
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

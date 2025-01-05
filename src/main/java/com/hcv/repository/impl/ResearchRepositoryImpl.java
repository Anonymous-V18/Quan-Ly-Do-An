package com.hcv.repository.impl;

import com.hcv.constant.QueryConst;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.Research;
import com.hcv.repository.criteria.SearchCriteria;
import com.hcv.repository.criteria.SearchCriteriaConsumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResearchRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public ShowAllResponse<Research> searchCriteria(Integer page, Integer limit, String sortBy, String... search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Research> criteriaQuery = criteriaBuilder.createQuery(Research.class);
        Root<Research> root = criteriaQuery.from(Research.class);

        Predicate predicate = criteriaBuilder.conjunction();
        List<SearchCriteria> params = new ArrayList<>();

        if (search != null) {
            for (String s : search) {
                Pattern pattern = Pattern.compile(QueryConst.SEARCH_OPERATION);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    String key = matcher.group(1);
                    String operation = matcher.group(2);
                    String value = matcher.group(3);
                    params.add(new SearchCriteria(key, operation, value));
                }
            }

            SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(criteriaBuilder, predicate, root);
            params.forEach(searchCriteriaConsumer);
            predicate = searchCriteriaConsumer.getPredicate();
        }
        criteriaQuery.where(predicate);

        if (sortBy != null) {
            Pattern pattern = Pattern.compile(QueryConst.SORT_OPERATION);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String orderBy = matcher.group(1);
                String orderDirection = matcher.group(3);
                if (orderDirection.equalsIgnoreCase("ASC")) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderBy)));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderBy)));
                }
            }
        }

        List<Research> researches = entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();

        long totalElements = getTotalElements(params);
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);
        return ShowAllResponse.<Research>builder()
                .currentPage(page)
                .totalPages(totalPages)
                .totalElements((int) totalElements)
                .responses(researches)
                .build();
    }


    public Long getTotalElements(List<SearchCriteria> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Research> root = criteriaQuery.from(Research.class);

        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(criteriaBuilder, predicate, root);
        params.forEach(searchCriteriaConsumer);
        predicate = searchCriteriaConsumer.getPredicate();
        criteriaQuery.where(predicate);

        criteriaQuery.select(criteriaBuilder.count(root));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}

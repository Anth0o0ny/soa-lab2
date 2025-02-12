package org.anth0o0ny.repository;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.anth0o0ny.dto.PageDto;
import org.anth0o0ny.dto.PageMetadata;
import org.anth0o0ny.enums.Climate;
import org.anth0o0ny.enums.Government;
import org.anth0o0ny.enums.StandardOfLiving;
import org.anth0o0ny.exception.SortingFormatException;
import org.anth0o0ny.model.entity.City;
import org.anth0o0ny.utils.FilterCriterion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Stateless
public class CityRepository {

    private final List<String> allowedSortingFields = List.of("name", "coordinates.x", "coordinates.y", "id", "creationDate", "area", "population", "metersAboveSeaLevel", "climate", "government", "standardOfLiving", "age");

    @PersistenceContext(unitName = "CitySource")
    private EntityManager entityManager;

    public Optional<City> findById(int id) {
        return Optional.ofNullable(entityManager.find(City.class, id));
    }

    public PageDto<City> findAll(int page, int size, List<String> sortParams, List<FilterCriterion> filters) {
        System.out.println("Received request with params: page=" + page + ", size=" + size + ", filters=" + filters);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<City> cq = cb.createQuery(City.class);
        Root<City> city = cq.from(City.class);

        List<Predicate> predicates = buildPredicates(cb, city, filters);

        cq.where(predicates.toArray(new Predicate[0]));

        List<Order> orderList = buildOrderList(cb, city, sortParams);
        if (!orderList.isEmpty()) {
            cq.orderBy(orderList);
        }

        List<City> cities = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        System.out.println("Fetched cities: " + cities.size());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<City> cityCount = countQuery.from(City.class);

        countQuery.select(cb.count(cityCount));
        countQuery.where(buildPredicates(cb, cityCount, filters).toArray(new Predicate[0]));

        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return constructPage(cities, page, size, totalCount);
    }

    public void save(City city) {
        if (city.getId() == 0) {
            entityManager.persist(city);
        } else {
            entityManager.merge(city);
        }
    }

    public void delete(City city) {
        entityManager.remove(city);
    }

    // Delete a City by Climate
    public void deleteByClimate(Climate climate) {
        City city = entityManager.createQuery("SELECT c FROM City c WHERE c.climate = :climate", City.class)
                .setParameter("climate", climate)
                .setMaxResults(1)
                .getSingleResult();
        if (city != null) {
            entityManager.remove(city);
        }
    }

    // Get number of Cities with StandardOfLiving less than specified
    public long countByStandardOfLivingLessThan(StandardOfLiving standardOfLiving) {
        return entityManager.createQuery("SELECT COUNT(c) FROM City c WHERE c.standardOfLiving < :standardOfLiving", Long.class)
                .setParameter("standardOfLiving", standardOfLiving)
                .getSingleResult();
    }

    // Get Cities by Government less than specified
    public List<City> findByGovernmentLessThan(Government government) {
        return entityManager.createQuery(
                        "SELECT c FROM City c WHERE c.government.ordinal() < :governmentOrdinal", City.class)
                .setParameter("governmentOrdinal", government.ordinal())
                .getResultList();
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<City> city, List<FilterCriterion> filters) {
        List<Predicate> predicates = new ArrayList<>();

        for (FilterCriterion filter : filters) {
            String fieldName = filter.getFieldName();
            String filterMode = filter.getFilterMode();
            String value = filter.getValue();

            Path<?> path = city;

            String[] fieldParts = fieldName.split("\\.");
            for (int i = 0; i < fieldParts.length - 1; i++) {
                path = ((From<?, ?>) path).join(fieldParts[i]);
            }
            String actualField = fieldParts[fieldParts.length - 1];

            switch (filterMode) {
                case "startsWith":
                    predicates.add(cb.like(path.get(actualField), value + "%"));
                    break;
                case "contains":
                    predicates.add(cb.like(path.get(actualField), "%" + value + "%"));
                    break;
                case "notContains":
                    predicates.add(cb.notLike(path.get(actualField), "%" + value + "%"));
                    break;
                case "endsWith":
                    predicates.add(cb.like(path.get(actualField), "%" + value));
                    break;
                case "equals":
                    predicates.add(cb.equal(path.get(actualField), value));
                    break;
                case "notEquals":
                    predicates.add(cb.notEqual(path.get(actualField), value));
                    break;
                case "lt":
                    if (actualField.equals("climate")) {
                        predicates.add(cb.lessThan(path.get(actualField), Climate.valueOf(value)));
                    }
                    else if (actualField.equals("government")) {
                        predicates.add(cb.lessThan(path.get(actualField), Government.valueOf(value)));
                    }
                    else if (actualField.equals("standardOfLiving")) {
                        predicates.add(cb.lessThan(path.get(actualField), StandardOfLiving.valueOf(value)));
                    }
                    else {
                        predicates.add(cb.lessThan(path.get(actualField), value));
                    }
                    break;

                case "lte":
                    predicates.add(cb.lessThanOrEqualTo(path.get(actualField), value));
                    break;
                case "gt":
                    predicates.add(cb.greaterThan(path.get(actualField), value));
                    break;
                case "gte":
                    predicates.add(cb.greaterThanOrEqualTo(path.get(actualField), value));
                    break;
                case "dateIs":
                    predicates.add(cb.equal(path.get(actualField), LocalDate.parse(value)));
                    break;
                case "dateIsNot":
                    predicates.add(cb.notEqual(path.get(actualField), LocalDate.parse(value)));
                    break;
                case "dateBefore":
                    predicates.add(cb.lessThan(path.get(actualField), LocalDate.parse(value)));
                    break;
                case "dateAfter":
                    predicates.add(cb.greaterThan(path.get(actualField), LocalDate.parse(value)));
                    break;
                case "in":
                    if (actualField.equals("climate")) {
                        String[] climateValues = value.split(",");
                        List<Climate> climateList = Arrays.stream(climateValues)
                                .map(String::trim)
                                .map(Climate::valueOf)
                                .collect(Collectors.toList());
                        predicates.add(path.get(actualField).in(climateList));
                    }
                    else if (actualField.equals("government")) {
                        String[] governmentValues = value.split(",");
                        List<Government> governmentList = Arrays.stream(governmentValues)
                                .map(String::trim)
                                .map(Government::valueOf)
                                .collect(Collectors.toList());
                        predicates.add(path.get(actualField).in(governmentList));
                    }
                    else if (actualField.equals("standardOfLiving")) {
                        String[] standardOfLivingValues = value.split(",");
                        List<StandardOfLiving> standardOfLivingList = Arrays.stream(standardOfLivingValues)
                                .map(String::trim)
                                .map(StandardOfLiving::valueOf)
                                .collect(Collectors.toList());
                        predicates.add(path.get(actualField).in(standardOfLivingList));
                    }

                    break;

            }
        }
        return predicates;
    }

    private List<Order> buildOrderList(CriteriaBuilder cb, Root<City> root, List<String> sortParams) {
        List<Order> orders = new ArrayList<>();

        if (sortParams != null) {
            for (String sortParam : sortParams) {
                String[] parts = sortParam.split(",");
                if (parts.length != 2 || !(parts[1].equals("asc") || parts[1].equals("desc")) || !allowedSortingFields.contains(parts[0])) {
                    throw new SortingFormatException();
                }

                String field = parts[0].trim();
                String direction = parts[1].trim().toLowerCase();

                From<?, ?> path = root;

                String[] fieldParts = field.split("\\.");
                for (int i = 0; i < fieldParts.length - 1; i++) {
                    path = path.join(fieldParts[i]);
                }

                String actualField = fieldParts[fieldParts.length - 1];

                if ("asc".equals(direction)) {
                    orders.add(cb.asc(path.get(actualField)));
                } else if ("desc".equals(direction)) {
                    orders.add(cb.desc(path.get(actualField)));
                } else {
                    throw new IllegalArgumentException("Unsupported sort direction: " + direction);
                }
            }
        }

        return orders;
    }

    private PageDto<City> constructPage(List<City> cities, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PageDto<>(cities, new PageMetadata(size, page, totalElements, totalPages));
    }
}


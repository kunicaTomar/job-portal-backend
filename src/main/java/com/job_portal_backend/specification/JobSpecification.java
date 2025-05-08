package com.job_portal_backend.specification;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.job_portal_backend.dto.JobSearchRequest;
import com.job_portal_backend.models.Job;

public class JobSpecification {

    public static Specification<Job> getJobBySearch(JobSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getJobId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("JobId"), request.getJobId()));
            }
            if (request.getJobTitle() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder
                        .lower(root.get("jobTitle")), "%" + request.getJobTitle().toLowerCase() + "%"));
            }
            if (request.getJobDescription() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder
                        .lower(root.get("jobDescription")), "%" + request.getJobDescription().toLowerCase() + "%"));
            }
            if (request.getLocation() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")),
                        "%" + request.getLocation().toLowerCase() + "%"));
            }

            if (request.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), request.getType()));
            }

            if (request.getWorkModel() != null) {
                predicates.add(criteriaBuilder.equal(root.get("workModel"), request.getWorkModel()));
            }

            if (request.getSkills() != null && !request.getSkills().isEmpty()) {
                predicates.add(root.get("skills").in(request.getSkills()));
            }
            if (request.getQualification() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("qualification")),
                        "%" + request.getQualification().toLowerCase() + "%"));
            }

            if (request.getJobpostedOn() != null) {
                predicates.add(criteriaBuilder.equal(root.get("jobpostedOn"), request.getJobpostedOn()));
            }

            if (request.getApplicattionLastDate() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("applicattionLastDate"), request.getApplicattionLastDate()));
            }

            // Sorting logic
            if (request.getSortBy() != null && request.getSortDirection() != null) {
                Path<Object> sortField = root.get(request.getSortBy());

                if (request.getSortDirection().equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(sortField)); // SQL: ORDER BY column ASC
                } else {
                    query.orderBy(criteriaBuilder.desc(sortField)); // SQL: ORDER BY column DESC
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

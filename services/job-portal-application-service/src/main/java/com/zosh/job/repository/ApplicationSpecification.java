package com.zosh.job.repository;

import com.zosh.job.domain.AishortListStatus;
import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.model.Application;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class ApplicationSpecification {
  public static Specification<Application> forCompanyWithFilters(
      Long companyId,
      Long jobId,
      ApplicationStatus status,
      Boolean isStarred,
      AishortListStatus aishortListStatus,
      Integer minAiScore) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      predicates.add(cb.equal(root.get("companyId"), companyId));
      if (jobId != null) {
        predicates.add(cb.equal(root.get("jobId"), jobId));
      }
      if (status != null) {
        predicates.add(cb.equal(root.get("status"), status));
      }
      if (isStarred != null) {
        predicates.add(cb.equal(root.get("isStarred"), isStarred));
      }
      if (aishortListStatus != null) {
        predicates.add(cb.equal(root.get("aishortListStatus"), aishortListStatus));
      }
      if (minAiScore != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("aiScore"), minAiScore));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }

  public static Specification<Application> forJobWithFilters(
      Long jobId,
      ApplicationStatus status,
      Boolean isStarred,
      AishortListStatus aishortListStatus,
      Integer minAiScore) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      predicates.add(cb.equal(root.get("jobId"), jobId));
      if (status != null) {
        predicates.add(cb.equal(root.get("status"), status));
      }
      if (isStarred != null) {
        predicates.add(cb.equal(root.get("isStarred"), isStarred));
      }
      if (aishortListStatus != null) {
        predicates.add(cb.equal(root.get("aishortListStatus"), aishortListStatus));
      }
      if (minAiScore != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("aiScore"), minAiScore));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}

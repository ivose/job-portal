package com.zosh.job.repository;

import com.zosh.job.domain.JobStatus;
import com.zosh.job.model.Job;
import com.zosh.job.payload.JobSearchRequest;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

  private JobSpecification() {}

  public static Specification<Job> build(JobSearchRequest req) {

    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      predicates.add(cb.isTrue(root.get("active")));

      JobStatus status = req.getJobStatus() != null ? req.getJobStatus() : JobStatus.OPEN;

      predicates.add(cb.equal(root.get("jobStatus"), status));

      if (req.getKeyword() != null && !req.getKeyword().isBlank()) {

        String keyword = "%" + req.getKeyword().trim().toLowerCase() + "%";

        predicates.add(
            cb.or(
                cb.like(cb.lower(root.get("title")), keyword),
                cb.like(cb.lower(root.get("description")), keyword)));
      }

      if (req.getCompanyId() != null) {
        predicates.add(cb.equal(root.get("companyId"), req.getCompanyId()));
      }

      if (req.getJobType() != null) {
        predicates.add(cb.equal(root.get("jobType"), req.getJobType()));
      }

      if (req.getExperienceLevel() != null) {
        predicates.add(cb.equal(root.get("experienceLevel"), req.getExperienceLevel()));
      }

      if (req.getLocation() != null && !req.getLocation().isBlank()) {

        String location = "%" + req.getLocation().trim().toLowerCase() + "%";

        predicates.add(
            cb.or(
                cb.like(cb.lower(root.get("location").get("city")), location),
                cb.like(cb.lower(root.get("location").get("state")), location),
                cb.like(cb.lower(root.get("location").get("country")), location),
                cb.like(cb.lower(root.get("location").get("address")), location)));
      }

      if (req.getMinSalary() != null) {
        predicates.add(
            cb.greaterThanOrEqualTo(root.get("salaryRange").get("maxSalary"), req.getMinSalary()));
      }

      if (req.getMaxSalary() != null) {
        predicates.add(
            cb.lessThanOrEqualTo(root.get("salaryRange").get("minSalary"), req.getMaxSalary()));
      }

      if (req.getMinOpenings() != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("openings"), req.getMinOpenings()));
      }

      if (req.getMaxOpenings() != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("openings"), req.getMaxOpenings()));
      }

      predicates.add(
          cb.or(
              cb.isNull(root.get("expiredAt")),
              cb.greaterThanOrEqualTo(root.get("expiredAt"), LocalDate.now())));

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}

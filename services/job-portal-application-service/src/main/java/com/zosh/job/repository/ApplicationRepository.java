package com.zosh.job.repository;

import com.zosh.job.model.Application;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicationRepository
    extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
  List<Application> findByCandidateId(Long candidateId);

  List<Application> findByJobId(Long jobId);

  boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
}

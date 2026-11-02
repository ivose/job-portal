package com.zosh.job.repository;

import com.zosh.job.model.SavedJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
  List<SavedJob> findByCandidateId(Long candidateId);

  boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
}

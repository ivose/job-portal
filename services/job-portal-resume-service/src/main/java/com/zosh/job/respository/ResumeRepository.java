package com.zosh.job.respository;

import com.zosh.job.model.Resume;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
  List<Resume> findByCandidateIdAndIsActiveTrue(Long candidateId);

  Optional<Resume> findByCandidateIdAndIsDefaultTrue(Long candidateId);
}

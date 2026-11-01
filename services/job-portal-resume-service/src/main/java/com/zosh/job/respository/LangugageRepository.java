package com.zosh.job.respository;

import com.zosh.job.model.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LangugageRepository extends JpaRepository<Language, Long> {
  List<Language> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}

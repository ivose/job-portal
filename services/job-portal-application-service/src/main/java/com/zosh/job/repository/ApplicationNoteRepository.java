package com.zosh.job.repository;

import com.zosh.job.model.ApplicationNote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicationNoteRepository
    extends JpaRepository<ApplicationNote, Long>, JpaSpecificationExecutor<ApplicationNote> {
  List<ApplicationNote> findByApplicationId(Long applicationID);
}

package com.zosh.job.service;

import com.zosh.job.dto.SavedJobResponse;
import com.zosh.job.payload.SavedJobRequest;
import java.util.List;

public interface SavedJobService {
  SavedJobResponse saveJob(Long candidateId, SavedJobRequest req) throws Exception;

  void unsaveJob(Long candidateId, Long savedJobID) throws Exception;

  List<SavedJobResponse> getSavedJob(Long candidateId) throws Exception;

  boolean isSaved(Long candidateId, Long jobID) throws Exception;
}

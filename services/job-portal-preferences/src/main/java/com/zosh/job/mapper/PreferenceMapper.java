package com.zosh.job.mapper;

import com.zosh.job.dto.SavedJobResponse;
import com.zosh.job.model.SavedJob;

public class PreferenceMapper {
  public static SavedJobResponse toSavedJobResponse(SavedJob savedJob) {
    return savedJob == null
        ? null
        : SavedJobResponse.builder()
            .id(savedJob.getId())
            .candidateId(savedJob.getCandidateId())
            .jobId(savedJob.getJobId())
            .savedAt(savedJob.getSavedAt())
            .build();
  }
}

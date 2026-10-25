package com.zosh.job.service;

import com.zosh.job.dto.JobTagResponse;
import com.zosh.job.model.JobTag;
import com.zosh.job.payload.JobTagRequest;
import java.util.List;
import java.util.Set;

public interface JobTagService {
  JobTagResponse createJobTag(JobTagRequest req) throws Exception;

  List<JobTagResponse> getAllTags() throws Exception;

  JobTagResponse getTagById(Long id) throws Exception;

  JobTagResponse updateTag(Long id, JobTagRequest req) throws Exception;

  void deleteTag(Long id) throws Exception;

  Set<JobTag> getTagsByIds(Set<Long> ids) throws Exception;
}

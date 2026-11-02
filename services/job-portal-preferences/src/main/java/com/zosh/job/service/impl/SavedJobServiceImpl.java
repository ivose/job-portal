package com.zosh.job.service.impl;

import com.zosh.job.dto.SavedJobResponse;
import com.zosh.job.mapper.PreferenceMapper;
import com.zosh.job.model.SavedJob;
import com.zosh.job.payload.SavedJobRequest;
import com.zosh.job.repository.SavedJobRepository;
import com.zosh.job.service.SavedJobService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedJobServiceImpl implements SavedJobService {

  private final SavedJobRepository savedJobRepository;

  @Override
  public SavedJobResponse saveJob(Long candidateId, SavedJobRequest req) throws Exception {
    if (isSaved(candidateId, req.getJobId())) {
      throw new Exception("Job already saved");
    }
    SavedJob savedJob = SavedJob.builder().candidateId(candidateId).jobId(req.getJobId()).build();
    return PreferenceMapper.toSavedJobResponse(savedJobRepository.save(savedJob));
  }

  @Override
  public void unsaveJob(Long candidateId, Long savedJobID) throws Exception {
    SavedJob savedJob =
        savedJobRepository.findById(savedJobID).orElseThrow(() -> new Exception("Job not found"));
    if (!savedJob.getCandidateId().equals(candidateId)) {
      throw new Exception("Job not saved");
    }
    savedJobRepository.delete(savedJob);
  }

  @Override
  public List<SavedJobResponse> getSavedJob(Long candidateId) throws Exception {
    return savedJobRepository.findByCandidateId(candidateId).stream()
        .map(PreferenceMapper::toSavedJobResponse)
        .toList();
  }

  @Override
  public boolean isSaved(Long candidateId, Long jobId) throws Exception {
    return savedJobRepository.existsByCandidateIdAndJobId(candidateId, jobId);
  }
}

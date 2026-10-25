package com.zosh.job.service.impl;

import com.zosh.job.dto.JobTagResponse;
import com.zosh.job.mapper.JobTagMapper;
import com.zosh.job.model.JobTag;
import com.zosh.job.payload.JobTagRequest;
import com.zosh.job.repository.JobTagsRepository;
import com.zosh.job.service.JobTagService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JobTaglServiceImpl implements JobTagService {

  private final JobTagsRepository jobTagsRepository;

  @Override
  public JobTagResponse createJobTag(JobTagRequest req) throws Exception {
    if (req.getName().isEmpty()) {
      throw new Exception("Name cannot be empty");
    }
    if (jobTagsRepository.existsByName(req.getName())) {
      throw new Exception("Tag already exists");
    }
    String slug = generateUniqueSlug(req.getName());
    JobTag jobTag = JobTag.builder().name(req.getName()).slug(slug).build();
    JobTag savedJobTag = jobTagsRepository.save(jobTag);
    return JobTagMapper.toJobTagResponse(savedJobTag);
  }

  @Override
  public List<JobTagResponse> getAllTags() throws Exception {
    return jobTagsRepository.findByActiveTrue().stream()
        .map(JobTagMapper::toJobTagResponse)
        .collect(Collectors.toList());
  }

  @Override
  public JobTagResponse getTagById(Long id) throws Exception {
    JobTag Tag = jobTagsRepository.findById(id).orElseThrow(() -> new Exception("Tag not found"));
    return JobTagMapper.toJobTagResponse(Tag);
  }

  @Override
  public JobTagResponse updateTag(Long id, JobTagRequest req) throws Exception {
    JobTag tag = jobTagsRepository.findById(id).orElseThrow(() -> new Exception("Tag not found"));
    if (!tag.getName().equals(req.getName()) && jobTagsRepository.existsByName(req.getName())) {
      throw new Exception("Tag already exists");
    }
    tag.setName(req.getName());
    JobTag updated = jobTagsRepository.save(tag);
    return JobTagMapper.toJobTagResponse(updated);
  }

  @Override
  public void deleteTag(Long id) throws Exception {
    JobTag Tag = jobTagsRepository.findById(id).orElseThrow(() -> new Exception("Tag not found"));
    jobTagsRepository.delete(Tag);
  }

  @Override
  public Set<JobTag> getTagsByIds(Set<Long> ids) throws Exception {
    List<JobTag> tags = jobTagsRepository.findAllById(ids);
    return new HashSet<>(tags);
  }

  private String generateUniqueSlug(String name) {
    String base =
        name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]", "-");
    if (!jobTagsRepository.existsBySlug(base)) {
      return base;
    }
    int counter = 1;
    while (jobTagsRepository.existsBySlug(base + "-" + counter)) {
      counter++;
    }
    return base + "-" + counter;
  }
}

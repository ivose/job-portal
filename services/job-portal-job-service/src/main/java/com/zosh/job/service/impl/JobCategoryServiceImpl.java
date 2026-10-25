package com.zosh.job.service.impl;

import com.zosh.job.dto.JobCategoryResponse;
import com.zosh.job.mapper.JobCategoryMapper;
import com.zosh.job.model.JobCategory;
import com.zosh.job.payload.JobCategoryRequest;
import com.zosh.job.repository.JobCategoryRepository;
import com.zosh.job.service.JobCategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JobCategoryServiceImpl implements JobCategoryService {
  private final JobCategoryRepository jobCategoryRepository;

  @Override
  public JobCategoryResponse createJobCategory(JobCategoryRequest req) throws Exception {
    if (jobCategoryRepository.existsByName(req.getName())) {
      throw new Exception("Category already exists");
    }
    JobCategory parent = null;
    if (req.getParentId() != null) {
      parent = getCategoryEntityById(req.getParentId());
    }
    String slug = generateUniqueSlug(req.getName());

    JobCategory jobCategory =
        JobCategory.builder()
            .name(req.getName())
            .description(req.getDescription())
            .iconUrl(req.getIconUrl())
            .slug(slug)
            .parent(parent)
            .active(true)
            .build();

    JobCategory saved = jobCategoryRepository.save(jobCategory);
    return JobCategoryMapper.toResponse(saved, true);
  }

  @Override
  public List<JobCategoryResponse> getAllCategories() throws Exception {
    return jobCategoryRepository.findByActiveTrue().stream()
        .map(sub -> JobCategoryMapper.toResponse(sub, false))
        .collect(Collectors.toList());
  }

  @Override
  public JobCategoryResponse getCategoryById(Long id) throws Exception {
    JobCategory category = getCategoryEntityById(id);
    return JobCategoryMapper.toResponse(category, true);
  }

  @Override
  public JobCategoryResponse updateCategory(Long id, JobCategoryRequest req) throws Exception {
    JobCategory category = getCategoryEntityById(id);

    if (!category.getName().equals(req.getName())
        && jobCategoryRepository.existsByName(req.getName())) {
      throw new Exception("Category already exists");
    }

    if (!category.getName().equals(req.getName())) {
      category.setSlug(generateUniqueSlug(req.getName()));
    }

    category.setName(req.getName());
    category.setDescription(req.getDescription());
    category.setIconUrl(req.getIconUrl());

    JobCategory parent = null;

    if (req.getParentId() != null) {

      if (id.equals(req.getParentId())) {
        throw new Exception("Category cannot be its own parent");
      }

      parent = getCategoryEntityById(req.getParentId());

      JobCategory current = parent;

      while (current != null) {
        if (id.equals(current.getId())) {
          throw new Exception("Circular category hierarchy is not allowed");
        }
        current = current.getParent();
      }
    }

    category.setParent(parent);

    JobCategory saved = jobCategoryRepository.save(category);

    return JobCategoryMapper.toResponse(saved, true);
  }

  @Override
  public void deleteCategory(Long id) throws Exception {
    JobCategory category = getCategoryEntityById(id);
    category.setActive(false);
    jobCategoryRepository.save(category);
  }

  @Override
  public JobCategory getCategoryEntityById(Long id) throws Exception {
    return jobCategoryRepository
        .findById(id)
        .orElseThrow(() -> new Exception("Category not found"));
  }

  private String generateUniqueSlug(String name) {
    String base =
        name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]", "-");
    if (!jobCategoryRepository.existsBySlug(base)) {
      return base;
    }
    int counter = 1;
    while (jobCategoryRepository.existsBySlug(base + "-" + counter)) {
      counter++;
    }
    return base + "-" + counter;
  }
}

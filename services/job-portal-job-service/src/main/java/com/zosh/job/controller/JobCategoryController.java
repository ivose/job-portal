package com.zosh.job.controller;

import com.zosh.job.dto.JobCategoryResponse;
import com.zosh.job.payload.JobCategoryRequest;
import com.zosh.job.service.JobCategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {

  private final JobCategoryService jobCategoryService;

  @PostMapping
  public ResponseEntity<JobCategoryResponse> createJobCategory(
      @Valid @RequestBody JobCategoryRequest request) throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(jobCategoryService.createJobCategory(request));
  }

  @GetMapping
  public ResponseEntity<List<JobCategoryResponse>> getAllCategories() throws Exception {

    return ResponseEntity.ok(jobCategoryService.getAllCategories());
  }

  @GetMapping("/{id}")
  public ResponseEntity<JobCategoryResponse> getCategoryById(@PathVariable Long id)
      throws Exception {

    return ResponseEntity.ok(jobCategoryService.getCategoryById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<JobCategoryResponse> updateCategory(
      @PathVariable Long id, @Valid @RequestBody JobCategoryRequest request) throws Exception {

    return ResponseEntity.ok(jobCategoryService.updateCategory(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws Exception {

    jobCategoryService.deleteCategory(id);

    return ResponseEntity.noContent().build();
  }
}

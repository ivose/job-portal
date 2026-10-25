package com.zosh.job.controller;

import com.zosh.job.dto.JobTagResponse;
import com.zosh.job.mapper.JobTagMapper;
import com.zosh.job.model.JobTag;
import com.zosh.job.payload.JobTagRequest;
import com.zosh.job.service.JobTagService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job-tags")
@RequiredArgsConstructor
public class JobTagController {

  private final JobTagService jobTagService;

  @PostMapping
  public ResponseEntity<JobTagResponse> createJobTag(@Valid @RequestBody JobTagRequest request)
      throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED).body(jobTagService.createJobTag(request));
  }

  @GetMapping
  public ResponseEntity<List<JobTagResponse>> getAllTags() throws Exception {

    return ResponseEntity.ok(jobTagService.getAllTags());
  }

  @GetMapping("/{id}")
  public ResponseEntity<JobTagResponse> getTagById(@PathVariable Long id) throws Exception {

    return ResponseEntity.ok(jobTagService.getTagById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<JobTagResponse> updateTag(
      @PathVariable Long id, @Valid @RequestBody JobTagRequest request) throws Exception {

    return ResponseEntity.ok(jobTagService.updateTag(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable Long id) throws Exception {

    jobTagService.deleteTag(id);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/by-ids")
  public ResponseEntity<Set<JobTagResponse>> getTagsByIds(@RequestBody Set<Long> ids)
      throws Exception {

    Set<JobTag> Tags = jobTagService.getTagsByIds(ids);

    Set<JobTagResponse> responses =
        Tags.stream().map(JobTagMapper::toJobTagResponse).collect(Collectors.toSet());

    return ResponseEntity.ok(responses);
  }
}

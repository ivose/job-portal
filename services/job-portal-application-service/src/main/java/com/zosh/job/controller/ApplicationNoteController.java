package com.zosh.job.controller;

import com.zosh.job.dto.ApplicationNoteResponse;
import com.zosh.job.payload.AddApplicationNoteRequest;
import com.zosh.job.service.ApplicationNoteService;
import com.zosh.job.service.ApplicationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application-notes")
@RequiredArgsConstructor
public class ApplicationNoteController {

  private final ApplicationService applicationService;
  private final ApplicationNoteService applicationNoteService;

  @PostMapping("/{applicationId}")
  public ResponseEntity<ApplicationNoteResponse> addNote(
      @PathVariable Long applicationId,
      @RequestHeader("X-User-Id") Long employerId,
      @RequestBody @Valid AddApplicationNoteRequest req)
      throws Exception {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(applicationNoteService.addNote(applicationId, employerId, req));
  }

  @GetMapping("/{applicationId}")
  public ResponseEntity<List<ApplicationNoteResponse>> getNotes(
      @PathVariable Long applicationId, @RequestHeader("X-User-Id") Long employerId)
      throws Exception {
    return ResponseEntity.ok(
        applicationNoteService.getNotesByApplication(applicationId, employerId));
  }

  @DeleteMapping("/{applicationId}/{noteId}")
  public ResponseEntity<Void> deleteNote(
      @PathVariable Long applicationId,
      @PathVariable Long noteId,
      @RequestHeader("X-User-Id") Long employerId)
      throws Exception {
    applicationNoteService.delete(applicationId, noteId, employerId);
    return ResponseEntity.noContent().build();
  }
}

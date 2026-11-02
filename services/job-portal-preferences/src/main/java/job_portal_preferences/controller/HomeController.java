package job_portal_preferences.controller;

import com.zosh.job.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping
  public ResponseEntity<ApiResponse> home() {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("Service for managing candidate job preferences like saved jobs");
    apiResponse.setStatus(true);
    return ResponseEntity.ok(apiResponse);
  }
}

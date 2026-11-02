package com.zosh.job.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class JobClient {

  private final RestClient restClient;

  public JobClient(@Value("${job.service.url:http://localhost:5003}") String jobServiceUrl) {
    this.restClient = RestClient.builder().baseUrl(jobServiceUrl).build();
  }

  public JobInfo getJobById(Long jobId) {
    return restClient.get().uri("/api/jobs/{id}", jobId).retrieve().body(JobInfo.class);
  }

  public record JobInfo(Long id, CompanyInfo company, Long employerId) {}

  public record CompanyInfo(Long id) {}
}

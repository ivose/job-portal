package com.zosh.job.service;

import com.zosh.job.payload.JobAlertSugestResponse;
import com.zosh.job.payload.JobAlertSuggestRequest;
import com.zosh.job.payload.SearchEncanceRequest;
import com.zosh.job.payload.SearchEncanceResponse;

public interface SearchAiService {
  SearchEncanceResponse enhanceSearch(SearchEncanceRequest req) throws Exception;

  JobAlertSugestResponse suggestJobAlertCriteria(JobAlertSuggestRequest req) throws Exception;
}

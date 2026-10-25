package com.zosh.job.payload;

import com.zosh.job.domain.ExperienceLevel;
import com.zosh.job.domain.JobStatus;
import com.zosh.job.domain.JobType;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchRequest {
  private String keyword;
  private Long categoryId;
  private List<Long> skillIds;
  private Long companyId;
  private String location;
  private BigDecimal minSalary;
  private BigDecimal maxSalary;
  private JobType jobType;
  private ExperienceLevel experienceLevel;
  private JobStatus jobStatus;
  private Integer minOpenings;
  private Integer maxOpenings;
}

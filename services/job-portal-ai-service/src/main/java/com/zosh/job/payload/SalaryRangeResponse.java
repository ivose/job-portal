package com.zosh.job.payload;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SalaryRangeResponse {

  private BigDecimal minSalary;
  private BigDecimal maxSalary;
  private String currency;
  private String period;
  private String marketInsight;
}

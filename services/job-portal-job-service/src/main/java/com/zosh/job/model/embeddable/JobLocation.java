package com.zosh.job.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Builder
public class JobLocation {
  private String address;
  private String city;
  private String state;
  private String country;
  private String zipCode;
}

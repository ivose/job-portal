package com.zosh.job.payload;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiTextResponse {
  private String content;
  @Builder.Default private LocalDateTime generatedAt = LocalDateTime.now();
}

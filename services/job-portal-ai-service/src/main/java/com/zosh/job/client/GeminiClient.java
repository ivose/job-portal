package com.zosh.job.client;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.zosh.job.config.GeminiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiClient {

  private final Client genAiClient;
  private final GeminiProperties geminiProperties;

  public String generateText(String prompt) throws Exception {
    return generateText(
        prompt, geminiProperties.getTemperature(), geminiProperties.getMaxOutputToken());
  }

  public String generateText(String prompt, double temperature, int maxTokens) throws Exception {
    try {
      GenerateContentConfig config =
          GenerateContentConfig.builder()
              .temperature((float) temperature)
              .maxOutputTokens(maxTokens)
              .build();
      GenerateContentResponse response =
          genAiClient.models.generateContent(geminiProperties.getModel(), prompt, config);
      String text = response.text();
      return text;
    } catch (Exception e) {
      throw new Exception("Failed to get response from gemini: " + e.getMessage());
    }
  }
}

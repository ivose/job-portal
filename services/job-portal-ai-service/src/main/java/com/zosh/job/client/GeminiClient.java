package com.zosh.job.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.zosh.job.config.GeminiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiClient {

  private final Client genAiClient;
  private final GeminiProperties geminiProperties;
  private final ObjectMapper objectMapper;

  public String generateText(String systemInstructions, String prompt) throws Exception {
    return generateText(
        systemInstructions,
        prompt,
        geminiProperties.getTemperature(),
        geminiProperties.getMaxOutputToken());
  }

  public <T> T generateJson(String systemInstructions, String prompt, Class<T> responseType)
      throws Exception {
    return callJson(systemInstructions, prompt, responseType);
  }

  public String generateText(
      String systemInstructions, String prompt, double temperature, int maxTokens)
      throws Exception {
    return callText(systemInstructions, prompt, (float) temperature, maxTokens);
  }

  private <T> T callJson(String systemInstruction, String prompt, Class<T> responseType)
      throws Exception {
    try {
      GenerateContentConfig config =
          buildConfig(systemInstruction, 0.3f, geminiProperties.getMaxOutputToken(), true);
      GenerateContentResponse response =
          genAiClient.models.generateContent(geminiProperties.getModel(), prompt, config);
      String text = response.text();
      return objectMapper.readValue(text, responseType);
    } catch (Exception e) {
      throw new Exception("Failed to get response from gemini: " + e.getMessage());
    }
  }

  private String callText(String systemInstruction, String prompt, float temperature, int maxTokens)
      throws Exception {
    try {
      GenerateContentConfig config = buildConfig(systemInstruction, temperature, maxTokens, false);
      GenerateContentResponse response =
          genAiClient.models.generateContent(geminiProperties.getModel(), prompt, config);
      String text = response.text();
      return text;
    } catch (Exception e) {
      throw new Exception("Failed to get response from gemini: " + e.getMessage());
    }
  }

  private GenerateContentConfig buildConfig(
      String systemInstruction, float temperature, int maxTokens, boolean jsonMode) {
    GenerateContentConfig.Builder builder =
        GenerateContentConfig.builder().temperature(temperature).maxOutputTokens(maxTokens);
    if (systemInstruction != null && !systemInstruction.isBlank()) {
      builder.systemInstruction(Content.fromParts(Part.fromText(systemInstruction)));
    }
    if (jsonMode) {
      builder.responseMimeType("application/json");
    }
    return builder.build();
  }
}

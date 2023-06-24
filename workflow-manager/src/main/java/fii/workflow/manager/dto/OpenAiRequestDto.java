package fii.workflow.manager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenAiRequestDto {

    String model;

    String prompt;

    double temperature;

    int max_tokens;

    public static OpenAiRequestDto.OpenAiRequestDtoBuilder defaultBuilder() {
        return OpenAiRequestDto.builder()
                .model("text-davinci-003")
                .temperature(0.6)
                .max_tokens(75);
    }
}

package fii.request.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiResponseDto {
    List<ChoiceDto> choices;
}

package fii.workflow.manager.service.htttpclient;

import fii.workflow.manager.dto.OpenAiRequestDto;
import fii.workflow.manager.dto.OpenAiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "open-ai", url = "https://api.openai.com")
public interface OpenAiClient {
    @PostMapping(value = "/v1/completions", consumes = MediaType.APPLICATION_JSON_VALUE)
    OpenAiResponseDto getCompletion(@RequestHeader("Authorization") String authorization,
                                    OpenAiRequestDto request);
}

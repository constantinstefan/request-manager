package fii.request.manager.service;

import fii.request.manager.domain.ChatGptStep;
import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.OpenAiRequestDto;
import fii.request.manager.dto.OpenAiResponseDto;
import fii.request.manager.service.htttpclient.OpenAiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("ChatGptStepRunnerService")
public class ChatGptStepRunnerService implements StepRunnerService {

    private OpenAiClient openAiClient;

    private ChatGptStepService chatGptStepService;

    @Value("${openai.key}")
    private String openAiKey;

    @Autowired
    ChatGptStepRunnerService(OpenAiClient openAiClient, ChatGptStepService chatGptStepService) {
        this.openAiClient = openAiClient;
        this.chatGptStepService = chatGptStepService;
    }

    String getCompletion(String prompt){
        String authorization = "Bearer " + openAiKey;
        System.out.println(authorization);
        OpenAiRequestDto request = OpenAiRequestDto.defaultBuilder()
                .prompt(prompt)
                .build();
        OpenAiResponseDto responseDto = openAiClient.getCompletion(authorization, request);
        return responseDto.getChoices().get(0).getText().trim();
    }

    @Override
    public void runServerStep(Long workflowStepId, WorkflowExecutionContext workflowExecutionContext) {
        ChatGptStep chatGptStep = chatGptStepService.getByWorkflowStepId(workflowStepId);
        workflowExecutionContext.setVariable(chatGptStep.getOutputVariable(),
                getCompletion(workflowExecutionContext.resolveVariablesInText(chatGptStep.getPrompt())));
        System.out.println(workflowExecutionContext);
    }
}

package fii.request.manager.mapper;

import fii.request.manager.domain.ChatGptStep;
import fii.request.manager.domain.EmailStep;
import fii.request.manager.dto.ChatGptStepDto;
import fii.request.manager.dto.EmailStepDto;

public class ChatGptStepMapper {

    public static ChatGptStep map(Long workflowStepId, ChatGptStepDto chatGptStepDto) {
        ChatGptStep chatGptStep = new ChatGptStep();
        chatGptStep.setWorkflowStepId(workflowStepId);
        chatGptStep.setPrompt(chatGptStepDto.getPrompt());
        chatGptStep.setOutputVariable(chatGptStepDto.getOutputVariable());
        return chatGptStep;
    }

    public static ChatGptStepDto map(ChatGptStep chatGptStep) {
        if(chatGptStep == null) return null;
        return ChatGptStepDto.builder()
                .prompt(chatGptStep.getPrompt())
                .outputVariable(chatGptStep.getOutputVariable())
                .workflowStepId(chatGptStep.getWorkflowStepId())
                .id(chatGptStep.getId())
                .build();
    }
}

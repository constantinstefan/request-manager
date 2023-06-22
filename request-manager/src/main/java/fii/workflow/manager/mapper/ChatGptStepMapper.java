package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.ChatGptStep;
import fii.workflow.manager.dto.ChatGptStepDto;

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

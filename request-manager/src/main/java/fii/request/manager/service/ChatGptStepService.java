package fii.request.manager.service;

import fii.request.manager.domain.ChatGptStep;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.ChatGptStepDto;
import fii.request.manager.mapper.ChatGptStepMapper;
import fii.request.manager.repository.ChatGptStepRepository;
import fii.request.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatGptStepService {

    private ChatGptStepRepository chatGptStepRepository;
    private WorkflowStepRepository workflowStepRepository;

    @Autowired
    ChatGptStepService(ChatGptStepRepository chatGptStepRepository,
                       WorkflowStepRepository workflowStepRepository) {
        this.chatGptStepRepository = chatGptStepRepository;
        this.workflowStepRepository = workflowStepRepository;
    }

    public ChatGptStepDto addChatGptStep(Long workflowStepId, ChatGptStepDto chatGptStepDto) {
        ChatGptStep chatGptStep = chatGptStepRepository.save(
                ChatGptStepMapper.map(workflowStepId, chatGptStepDto));
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        workflowStep.setChatGptStep(chatGptStep);
        workflowStepRepository.save(workflowStep);
        return ChatGptStepMapper.map(chatGptStep);
    }

    public ChatGptStep getByWorkflowStepId(Long workflowStepId) {
        return chatGptStepRepository.findByWorkflowStepId(workflowStepId).orElse(null);
    }
}

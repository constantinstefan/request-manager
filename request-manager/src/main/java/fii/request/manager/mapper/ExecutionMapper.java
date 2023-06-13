package fii.request.manager.mapper;

import fii.request.manager.domain.Execution;
import fii.request.manager.dto.ExecutionDto;

import java.time.LocalDateTime;

public class ExecutionMapper {
    public static ExecutionDto map(Execution execution) {
        return ExecutionDto.builder()
                .message(execution.getMessage())
                .workflowId(execution.getWorkflow().getId())
                .customer(execution.getCustomer())
                .startTime(execution.getStartTime())
                .stepNumber(execution.getStepNumber())
                .endTime(execution.getEndTime())
                .status(execution.getStatus())
                .id(execution.getId())
                .build();
    }

    public static ExecutionDto mapSuccess(ExecutionDto executionDto) {
        return ExecutionDto.builder()
                .message(executionDto.getMessage())
                .workflowId(executionDto.getId())
                .startTime(executionDto.getStartTime())
                .endTime(LocalDateTime.now())
                .stepNumber(executionDto.getStepNumber())
                .status("SUCCESS")
                .id(executionDto.getId())
                .build();
    }

    public static ExecutionDto mapFailure(ExecutionDto executionDto, String message) {
        return ExecutionDto.builder()
                .message(message)
                .workflowId(executionDto.getId())
                .startTime(executionDto.getStartTime())
                .endTime(LocalDateTime.now())
                .stepNumber(executionDto.getStepNumber())
                .status("FAILURE")
                .id(executionDto.getId())
                .build();
    }

    public static Execution map(ExecutionDto executionDto) {
        Execution execution = new Execution();
        execution.setMessage(executionDto.getMessage());
        execution.setStartTime(executionDto.getStartTime());
        execution.setEndTime(executionDto.getEndTime());
        execution.setStatus(executionDto.getStatus());
        execution.setStepNumber(executionDto.getStepNumber());
        execution.setId(executionDto.getId());
        return  execution;
    }
}

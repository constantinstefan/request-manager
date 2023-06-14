package fii.request.manager.mapper;

import fii.request.manager.domain.Execution;
import fii.request.manager.dto.ExecutionDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ExecutionMapper {
    public static ExecutionDto map(Execution execution) {
        return ExecutionDto.builder()
                .message(execution.getMessage())
                .workflowId(execution.getWorkflow().getId())
                .customer(execution.getCustomer())
                .startTime(execution.getStartTime().toEpochSecond(ZoneOffset.UTC))
                .stepNumber(execution.getStepNumber())
                .endTime(execution.getEndTime().toEpochSecond(ZoneOffset.UTC))
                .status(execution.getStatus())
                .id(execution.getId())
                .build();
    }

    public static ExecutionDto mapSuccess(ExecutionDto executionDto) {
        return ExecutionDto.builder()
                .message(executionDto.getMessage())
                .workflowId(executionDto.getWorkflowId())
                .startTime(executionDto.getStartTime())
                .endTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .stepNumber(executionDto.getStepNumber())
                .status("SUCCESS")
                .id(executionDto.getId())
                .build();
    }

    public static ExecutionDto mapFailure(ExecutionDto executionDto, String message) {
        return ExecutionDto.builder()
                .message(message)
                .workflowId(executionDto.getWorkflowId())
                .startTime(executionDto.getStartTime())
                .endTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .stepNumber(executionDto.getStepNumber())
                .status("FAILURE")
                .id(executionDto.getId())
                .build();
    }

    public static Execution map(ExecutionDto executionDto) {
        Execution execution = new Execution();
        execution.setMessage(executionDto.getMessage());
        execution.setStartTime( LocalDateTime.ofEpochSecond(executionDto.getStartTime(), 0, ZoneOffset.UTC) );
        execution.setEndTime( LocalDateTime.ofEpochSecond(executionDto.getStartTime(), 0, ZoneOffset.UTC) );
        execution.setStatus(executionDto.getStatus());
        execution.setStepNumber(executionDto.getStepNumber());
        execution.setId(executionDto.getId());
        return  execution;
    }
}

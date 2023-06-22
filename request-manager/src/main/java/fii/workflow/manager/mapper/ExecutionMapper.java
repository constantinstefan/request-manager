package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.Execution;
import fii.workflow.manager.dto.ExecutionDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ExecutionMapper {
    public static ExecutionDto map(Execution execution) {
        ExecutionDto.ExecutionDtoBuilder builder =  ExecutionDto.builder()
                .message(execution.getMessage())
                .workflowId(execution.getWorkflow().getId())
                .customer(CustomerMapper.map(execution.getCustomer()))
                .stepNumber(execution.getStepNumber())
                .status(execution.getStatus())
                .id(execution.getId());
        if(execution.getStartTime() != null) {
            builder.startTime(execution.getStartTime().toEpochSecond(ZoneOffset.UTC));
        }
        if(execution.getEndTime() != null) {
            builder.endTime(execution.getEndTime().toEpochSecond(ZoneOffset.UTC));
        }

        return builder.build();
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
        if(executionDto.getStartTime() != null) {
            execution.setStartTime(LocalDateTime.ofEpochSecond(executionDto.getStartTime(), 0, ZoneOffset.UTC));
        }
        if(executionDto.getEndTime() != null) {
            execution.setEndTime( LocalDateTime.ofEpochSecond(executionDto.getEndTime(), 0, ZoneOffset.UTC) );
        }
        execution.setStatus(executionDto.getStatus());
        execution.setStepNumber(executionDto.getStepNumber());
        execution.setId(executionDto.getId());
        return  execution;
    }
}

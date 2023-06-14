package fii.request.manager.controller;

import fii.request.manager.domain.*;
import fii.request.manager.dto.*;
import fii.request.manager.mapper.WorkflowExecutionContextMapper;
import fii.request.manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workflows")
public class WorkflowController {
    private WorkflowService workflowService;
    private WorkflowStepService workflowStepService;

    private WorkflowSharingService workflowSharingService;

    private FormFieldService formFieldService;

    private EditableHtmlService editableHtmlService;

    private DocumentRequestService documentRequestService;

    private EmailStepService emailStepService;

    private ChatGptStepService chatGptStepService;

    private WorkflowAsyncRunnerService workflowAsyncRunnerService;

    private CustomerService customerService;

    private ExecutionService executionService;

    @Autowired
    WorkflowController(WorkflowService workflowService,
                       WorkflowStepService workflowStepService,
                       WorkflowSharingService workflowSharingService,
                       WorkflowRunnerService workflowRunnerService,
                       FormFieldService formFieldService,
                       EditableHtmlService editableHtmlService,
                       DocumentRequestService documentRequestService,
                       EmailStepService emailStepService,
                       ChatGptStepService chatGptStepService,
                       WorkflowAsyncRunnerService workflowAsyncRunnerService,
                       CustomerService customerService,
                       ExecutionService executionService) {
        this.workflowService = workflowService;
        this.workflowStepService = workflowStepService;
        this.workflowSharingService = workflowSharingService;
        this.formFieldService = formFieldService;
        this.editableHtmlService = editableHtmlService;
        this.documentRequestService = documentRequestService;
        this.emailStepService = emailStepService;
        this.chatGptStepService = chatGptStepService = chatGptStepService;
        this.workflowAsyncRunnerService = workflowAsyncRunnerService;
        this.customerService =customerService;
        this.executionService = executionService;
    }

    @GetMapping(value="/{workflowId}")
    WorkflowDto getById(@PathVariable Long workflowId) {
        return workflowService.getWorkflowByIdFetchingSteps(workflowId);
    }

    @PostMapping
    WorkflowDto addWorkflow(@RequestBody Workflow workflow) {
        return workflowService.save(workflow);
    }

    @PostMapping(value = "/{workflowId}/steps")
    WorkflowStep addWorkflowStep(@PathVariable Long workflowId,
                                 @RequestBody WorkflowStep workflowStep) {
        return workflowStepService.addWorkflowStep(workflowId, workflowStep);
    }

    @GetMapping(value="/{workflowId}/steps")
    List<WorkflowStepDto> getWorkflowSteps(@PathVariable Long workflowId) {
        return workflowStepService.getWorkflowStepsFetchingChildren(workflowId);
    }

    @DeleteMapping(value = "/{workflowId}/steps/{stepId}")
    ResponseEntity deleteSteps(@PathVariable Long workflowId,
                               @PathVariable Long stepId) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        workflowStepService.removeStep(stepId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="/{workflowId}/steps/{stepId}")
    WorkflowStepDto putWorkflowSteps(@PathVariable Long workflowId,
                                     @PathVariable Long stepId,
                                     @RequestBody WorkflowStep workflowStep) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return workflowStepService.updateWorkflowStep(stepId, workflowStep);
    }

    @PostMapping(value="/{workflowId}/sharing")
    WorkflowSharing addWorkflowSharing(@PathVariable Long workflowId,
                                       @RequestBody WorkflowSharing workflowSharing) {
        return workflowSharingService.addWorkflowSharing(workflowId, workflowSharing);
    }

    @PostMapping(value = "/{workflowId}/steps/{stepId}/form-fields")
    List<FormField> addFormFields(@PathVariable Long workflowId,
                                  @PathVariable Long stepId,
                                  @RequestBody List<FormField> formFields) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return formFieldService.addFormFields(stepId, formFields);
    }

    @PostMapping(value = "/{workflowId}/steps/{stepId}/editable-html",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    EditableHtmlResponseDto addEditableHtml(@PathVariable Long workflowId,
                                            @PathVariable Long stepId,
                                            @RequestPart(value="file") MultipartFile file,
                                            @RequestPart(value="data") EditableHtmlDto htmlDto) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        htmlDto.setFile(file);
        htmlDto.setWorkflowStepId(stepId);
        return editableHtmlService.addEditableHtml(htmlDto);
    }

    @PostMapping(value= "/{workflowId}/steps/{stepId}/document-request")
    DocumentRequestDto addDocumentRequest(@PathVariable Long workflowId,
                                          @PathVariable Long stepId,
                                          @RequestBody DocumentRequest documentRequest) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return documentRequestService.addDocumentRequest(stepId, documentRequest);
    }

    @PutMapping(value= "/{workflowId}/steps/{stepId}/document-request")
    DocumentRequestDto putDocumentRequest(@PathVariable Long workflowId,
                                          @PathVariable Long stepId,
                                          @RequestBody DocumentRequest documentRequest) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return documentRequestService.updateDocumentRequest(stepId, documentRequest);
    }

    @PostMapping(value= "/{workflowId}/steps/{stepId}/email")
    EmailStep addEmailStep(@PathVariable Long workflowId,
                           @PathVariable Long stepId,
                           @RequestBody EmailStepDto emailStep) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return emailStepService.addEmailStep(stepId, emailStep);
    }
    @PutMapping(value= "/{workflowId}/steps/{stepId}/email")
    EmailStepDto putEmailStep(@PathVariable Long workflowId,
                              @PathVariable Long stepId,
                              @RequestBody EmailStep emailStep) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return emailStepService.updateEmailStep(stepId, emailStep);
    }

    @PostMapping(value= "/{workflowId}/steps/{stepId}/chatgpt-step")
    ChatGptStepDto addChatGptStep(@PathVariable Long workflowId,
                           @PathVariable Long stepId,
                           @RequestBody ChatGptStepDto chatGptStepDto) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return chatGptStepService.addChatGptStep(stepId, chatGptStepDto);
    }

    @GetMapping
    List<WorkflowDto> getAll() {
        return workflowService.getAllWorkflows();
    }

    @PostMapping(value= "/{workflowId}/execution", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    ExecutionDto doExecution(@PathVariable Long workflowId,
                             @RequestPart(value="file", required=false) List<MultipartFile> files,
                             @RequestPart(value="data", required = false) WorkflowExecutionContextDto workflowExecutionContextDto) {
        workflowExecutionContextDto.setFiles(files);
        WorkflowExecutionContext workflowExecutionContext = WorkflowExecutionContextMapper.map(workflowExecutionContextDto);
        Customer customer = customerService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        ExecutionDto execution = ExecutionDto.builder()
                .startTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .workflowId(workflowId)
                .status("IN_PROGRESS")
                .build();
        execution = executionService.saveExecution(workflowId, customer.getCustomerId(), execution);

        workflowAsyncRunnerService.runWorkflowAsync(workflowId, customer.getCustomerId(), workflowExecutionContext, execution);

        return execution;
    }

    @GetMapping(value = "/{workflowId}/executions")
    @ResponseBody
    List<ExecutionDto> getExecutions(@PathVariable Long workflowId) {
        return executionService.getExecutionsByWorkflowId(workflowId);
    }
}

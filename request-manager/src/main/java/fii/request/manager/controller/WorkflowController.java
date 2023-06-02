package fii.request.manager.controller;

import fii.request.manager.domain.*;
import fii.request.manager.dto.*;
import fii.request.manager.mapper.WorkflowExecutionContextMapper;
import fii.request.manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workflows")
public class WorkflowController {
    private WorkflowService workflowService;
    private WorkflowStepService workflowStepService;

    private WorkflowSharingService workflowSharingService;

    private WorkflowRunnerServiceImpl workflowRunnerService;

    private FormFieldService formFieldService;

    private EditableHtmlService editableHtmlService;

    private DocumentRequestService documentRequestService;

    private EmailStepService emailStepService;


    @Autowired
    WorkflowController(WorkflowService workflowService,
                       WorkflowStepService workflowStepService,
                       WorkflowSharingService workflowSharingService,
                       WorkflowRunnerServiceImpl workflowRunnerService,
                       FormFieldService formFieldService,
                       EditableHtmlService editableHtmlService,
                       DocumentRequestService documentRequestService,
                       EmailStepService emailStepService) {
        this.workflowService = workflowService;
        this.workflowStepService = workflowStepService;
        this.workflowSharingService = workflowSharingService;
        this.workflowRunnerService = workflowRunnerService;
        this.formFieldService = formFieldService;
        this.editableHtmlService = editableHtmlService;
        this.documentRequestService = documentRequestService;
        this.emailStepService = emailStepService;
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

    @PostMapping(value= "/{workflowId}/steps/{stepId}/email")
    EmailStep addEmailStep(@PathVariable Long workflowId,
                           @PathVariable Long stepId,
                           @RequestBody EmailStepDto emailStep) {
        workflowService.assertContainsWorkflowStep(workflowId, stepId);
        return emailStepService.addEmailStep(stepId, emailStep);
    }
    @GetMapping
    List<WorkflowDto> getAll() {
        return workflowService.getAllWorkflows();
    }

    @PostMapping(value= "/{workflowId}/execution", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<byte[]> doExecution(@PathVariable Long workflowId,
                                       @RequestPart(value="file", required=false) List<MultipartFile> files,
                                       @RequestPart(value="data", required = false) WorkflowExecutionContextDto workflowExecutionContextDto) {
        workflowExecutionContextDto.setFiles(files);
        WorkflowExecutionContext workflowExecutionContext = WorkflowExecutionContextMapper.map(workflowExecutionContextDto);
        workflowRunnerService.runWorkflow(workflowId, workflowExecutionContext);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "result.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(workflowExecutionContext.getFile("pdf-file"));
    }
}

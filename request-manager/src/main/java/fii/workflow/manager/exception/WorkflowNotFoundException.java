package fii.workflow.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkflowNotFoundException extends RuntimeException{
    public WorkflowNotFoundException() {
        super();
    }
}

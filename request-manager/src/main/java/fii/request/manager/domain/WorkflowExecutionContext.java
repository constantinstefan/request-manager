package fii.request.manager.domain;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Builder
public class WorkflowExecutionContext {
    @Getter Long workflowId;

    Map<String, String> variableValueByName;

    Map<String, byte[]> fileContentByFileName;

    public String getVariable(String key) {
        return variableValueByName.get(key);
    }

    public void setVariable(String key, String value) {
        if(value.startsWith("$")) {
            String otherKey = value.substring(1);
            String otherValue = variableValueByName.get(otherKey);
            variableValueByName.put(key, otherValue);
            return;
        }

        variableValueByName.put(key, value);
    }

    public byte[] getFile(String key) {
        return fileContentByFileName.get(key);
    }

    public void setFile(String key, byte[] fileContent) {
        fileContentByFileName.put(key, fileContent);
    }
}

package fii.request.manager.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Builder
public class WorkflowExecutionContext {
    @Getter Long workflowId;

    Map<String, String> variableValueByName;

    Map<String, String> fileNameByFileVariableName;

    Map<String, byte[]> fileContentByFileVariableName;

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
        return fileContentByFileVariableName.get(key);
    }

    public void setFile(String key, String fileName, byte[] fileContent) {
        fileContentByFileVariableName.put(key, fileContent);
        fileNameByFileVariableName.put(key, fileName);
    }

    public String resolveVariable(String variable) {
        if(variable == null) return null;
        String found = variableValueByName.get(variable.substring(1));
        return (found != null)
                ? found
                : variable;
    }

    public String resolveVariablesInText(String text) {
        if (text == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("\\$\\w+");
        Matcher matcher = pattern.matcher(text);
        StringBuffer resolvedText = new StringBuffer();

        while (matcher.find()) {
            String variable = matcher.group(0);
            String resolvedValue = resolveVariable(variable);
            String replacement = (resolvedValue != null) ? resolvedValue : matcher.group(0);
            matcher.appendReplacement(resolvedText, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(resolvedText);
        return resolvedText.toString();
    }


    public Map<String, byte[]> getFiles(String fileVariables) {
        return Arrays.asList(fileVariables.split(";")).stream()
                .map(fileVariable -> fileVariable.substring(1))
                .filter(fileVariable -> fileContentByFileVariableName.containsKey(fileVariable))
                .collect(Collectors.toMap(
                        fileVariable -> fileNameByFileVariableName.get(fileVariable),
                        fileVariable -> fileContentByFileVariableName.get(fileVariable)));
    }
}

package fii.workflow.manager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NameValueDto {
    String name;
    String value;
}

package com.aps.services.user.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbstractResponse {
    private int status;
    private String message;
    private String error;
    private String[] stackTrace;
    private Long id;
    private Map<String, Object> response;

    public AbstractResponse(Long id) {
        this.id = id;
    }

}

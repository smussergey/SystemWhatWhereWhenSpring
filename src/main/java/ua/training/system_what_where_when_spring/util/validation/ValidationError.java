package ua.training.system_what_where_when_spring.util.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errorsList = new ArrayList<>();

    public void addToErrorsList(String error) {
        errorsList.add(error);
    }

    public List<String> getErrors() {
        return errorsList;
    }
}

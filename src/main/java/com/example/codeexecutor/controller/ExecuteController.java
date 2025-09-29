package com.example.codeexecutor.controller;

import com.example.codeexecutor.model.ExecutionJob;
import com.example.codeexecutor.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ExecuteController {

    @Autowired
    private ExecutionService executionService;

    @PostMapping("/execute")
    public Map<String, String> executeCode(@RequestBody Map<String, Object> payload) {
        String language = (String) payload.get("language");
        String source = (String) payload.get("source");
        List<String> testCases = (List<String>) payload.get("testCases");
        String jobId = executionService.submitJob(language, source, testCases);
        return Map.of("jobId", jobId);
    }

    @GetMapping("/status/{jobId}")
    public ExecutionJob getJobStatus(@PathVariable String jobId) {
        return executionService.getJobStatus(jobId);
    }
}

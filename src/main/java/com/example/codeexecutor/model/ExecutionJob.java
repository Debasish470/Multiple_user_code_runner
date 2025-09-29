package com.example.codeexecutor.model;

import java.util.List;
import java.util.UUID;

public class ExecutionJob {
    private String jobId;
    private String language;
    private String source;
    private List<String> testCases;
    private String status; // PENDING, RUNNING, COMPLETED
    private String result;

    public ExecutionJob(String language, String source, List<String> testCases) {
        this.jobId = UUID.randomUUID().toString();
        this.language = language;
        this.source = source;
        this.testCases = testCases;
        this.status = "PENDING";
    }

    // Getters and setters
    public String getJobId() { return jobId; }
    public String getLanguage() { return language; }
    public String getSource() { return source; }
    public List<String> getTestCases() { return testCases; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}

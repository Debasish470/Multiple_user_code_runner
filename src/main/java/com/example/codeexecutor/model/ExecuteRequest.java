package com.example.codeexecutor.model;

import java.util.List;

public class ExecuteRequest {
    private String language;
    private String source;
    private List<String> testCases;

    // Getters & Setters
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public List<String> getTestCases() { return testCases; }
    public void setTestCases(List<String> testCases) { this.testCases = testCases; }
}

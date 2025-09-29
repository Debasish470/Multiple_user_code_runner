package com.example.codeexecutor.service;

import com.example.codeexecutor.model.ExecutionJob;
import com.example.codeexecutor.utils.DockerRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExecutionService {

    @Autowired
    private JobRepository jobRepository;

    public String submitJob(String language, String source, List<String> testCases) {
        ExecutionJob job = new ExecutionJob(language, source, testCases);
        jobRepository.save(job);
        runAsync(job);
        return job.getJobId();
    }

    @Async
    public void runAsync(ExecutionJob job) {
        job.setStatus("RUNNING");
        String result = DockerRunner.execute(job.getLanguage(), job.getSource(), job.getTestCases());
        job.setResult(result);
        job.setStatus("COMPLETED");
    }

    public ExecutionJob getJobStatus(String jobId) {
        return jobRepository.getJob(jobId);
    }
}

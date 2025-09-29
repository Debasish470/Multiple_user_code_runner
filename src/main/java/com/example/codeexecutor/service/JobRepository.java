package com.example.codeexecutor.service;

import com.example.codeexecutor.model.ExecutionJob;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JobRepository {
    private final ConcurrentHashMap<String, ExecutionJob> jobs = new ConcurrentHashMap<>();
    public void save(ExecutionJob job) { jobs.put(job.getJobId(), job); }
    public ExecutionJob getJob(String jobId) { return jobs.get(jobId); }
}

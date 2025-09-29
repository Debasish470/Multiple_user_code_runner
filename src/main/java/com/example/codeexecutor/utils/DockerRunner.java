package com.example.codeexecutor.utils;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DockerRunner {

    public static String execute(String language, String source, List<String> testCases) {
        try {
            Path workdir = Files.createTempDirectory("code-runner-");
            Path srcFile = workdir.resolve(getFileName(language));
            Files.writeString(srcFile, source);

            List<String> cmd = getDockerCommand(language, workdir);

            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));
            process.waitFor();

            Files.walk(workdir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);

            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String getFileName(String language) {
        return switch (language.toLowerCase()) {
            case "java" -> "Main.java";
            case "python" -> "main.py";
            case "javascript" -> "main.js";
            case "typescript" -> "main.ts";
            default -> throw new RuntimeException("Unsupported language");
        };
    }

    private static List<String> getDockerCommand(String language, Path workdir) {
        return switch (language.toLowerCase()) {
            case "java" -> List.of("docker", "run", "--rm", "-v",
                    workdir.toAbsolutePath() + ":/code", "-w", "/code",
                    "openjdk:21", "sh", "-c", "javac Main.java && java Main");
            case "python" -> List.of("docker", "run", "--rm", "-v",
                    workdir.toAbsolutePath() + ":/code", "-w", "/code",
                    "python:3.11-alpine", "python", "main.py");
            case "javascript" -> List.of("docker", "run", "--rm", "-v",
                    workdir.toAbsolutePath() + ":/code", "-w", "/code",
                    "node:20-alpine", "node", "main.js");
            case "typescript" -> List.of("docker", "run", "--rm", "-v",
                    workdir.toAbsolutePath() + ":/code", "-w", "/code",
                    "node:20-alpine", "sh", "-c", "npx ts-node main.ts");
            default -> throw new RuntimeException("Unsupported language");
        };
    }
}

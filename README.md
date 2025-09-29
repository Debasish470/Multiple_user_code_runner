mvn clean
mvn install
mvn spring-boot:run
curl -Method POST http://localhost:8080/api/v1/execute -Headers @{ "Content-Type" = "application/json" } -Body '{"language":"java","source":"public class Main { public static void main(String[] args) { System.out.println(\"Hello Ignite\"); } }","testCases":["Hello Ignite"]}'
curl -Method POST http://localhost:8080/api/v1/execute -Headers @{ "Content-Type" = "application/json" } -Body '{"language":"python","source":"for i in range(1,6): print(f\"5 x {i} = {5*i}\")","testCases":["5 x 1 = 5","5 x 2 = 10","5 x 3 = 15","5 x 4 = 25","5 x 5 = 30"]}'
curl -Method POST http://localhost:8080/api/v1/execute -Headers @{ "Content-Type" = "application/json" } -Body '{"language":"javascript","source":"console.log(\"User3 Hello\");","testCases":["User3 Hello"]}'


# Array to hold jobs
$jobs = @()

# --- User 1 (Java) ---
$response1 = curl -Method POST http://localhost:8080/api/v1/execute `
-Headers @{ "Content-Type" = "application/json" } `
-Body '{"language":"java","source":"public class Main { public static void main(String[] args) { System.out.println(\"User1 Hello\"); } }","testCases":["User1 Hello"]}'
$jobs += @{ User="User1"; JobId=($response1.Content | ConvertFrom-Json).jobId }
# --- User 2 (Python) ---
$response2 = curl -Method POST http://localhost:8080/api/v1/execute `
-Headers @{ "Content-Type" = "application/json" } `
-Body '{"language":"python","source":"print(\"User2 Hello\")","testCases":["User2 Hello"]}'
$jobs += @{ User="User2"; JobId=($response2.Content | ConvertFrom-Json).jobId }

# --- User 3 (JavaScript) ---
$response3 = curl -Method POST http://localhost:8080/api/v1/execute `
-Headers @{ "Content-Type" = "application/json" } `
-Body '{"language":"javascript","source":"console.log(\"User3 Hello\");","testCases":["User3 Hello"]}'
$jobs += @{ User="User3"; JobId=($response3.Content | ConvertFrom-Json).jobId }

# --- User 4 (TypeScript) ---
$response4 = curl -Method POST http://localhost:8080/api/v1/execute `
-Headers @{ "Content-Type" = "application/json" } `
-Body '{"language":"typescript","source":"console.log(\"User4 Hello\")","testCases":["User4 Hello"]}'
$jobs += @{ User="User4"; JobId=($response4.Content | ConvertFrom-Json).jobId }

Write-Host "`nAll jobs submitted. Polling results..."

# Polling loop
$allCompleted = $false
while (-not $allCompleted) {
    $allCompleted = $true
    foreach ($job in $jobs) {
        $statusResponse = curl http://localhost:8080/api/v1/status/$($job.JobId)
        $statusJson = $statusResponse.Content | ConvertFrom-Json
        Write-Host "$($job.User) | Status: $($statusJson.status) | Result: $($statusJson.result -replace '\r','')`n"
        if ($statusJson.status -ne "COMPLETED") {
            $allCompleted = $false
        }
    }
    if (-not $allCompleted) { Start-Sleep -Seconds 1 }
}

Write-Host "`nAll jobs completed!"

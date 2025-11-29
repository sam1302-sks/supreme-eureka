$ErrorActionPreference = "Stop"

Write-Host "Building project..."
.\mvnw.cmd clean install -DskipTests

if ($LASTEXITCODE -eq 0) {
    Write-Host "Starting application..."
    java -jar target/satops-dsl-0.0.1-SNAPSHOT.jar
} else {
    Write-Host "Build failed."
}

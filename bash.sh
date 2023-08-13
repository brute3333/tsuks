#!/bin/bash
# Build script

# Define the path to the root directory of your Maven project
project_dir="/home/bootcaamp_/Pictures/jhb7_brownfields_meg_whitman"

# Function to execute a command and check the exit status
execute_command() {
    local command="$1"
    echo "Executing: $command"
    (cd "$project_dir" && eval "$command")
    local exit_status=$?
    if [ $exit_status -ne 0 ]; then
        echo "Error: Command failed with exit status $exit_status"
        exit $exit_status
    fi
}

# Clean
execute_command "mvn clean"

# Verify
execute_command "mvn verify"

# Compile
execute_command "mvn compile"

# Test
execute_command "mvn test"

# Package
execute_command "mvn package"

# Success
echo "Build, test, and packaging completed successfully."

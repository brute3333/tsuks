
clean:
	$ mvn clean

# Verify
verify:
	$ mvn verify

# Compile
compile:
	$ mvn compile

# Test
test:
	$ mvn test

# Package
package:
	$ mvn package

# Success
success:
	@echo "Build, test, and packaging completed successfully."

# Default target
all: clean verify compile test package success

# Reference Test Iteration 1
reference-test-Itr1:
	@echo "Running Iteration 1 Reference Server Jar File"
	@java -jar libs/reference-server-0.1.0.jar & \
		server_pid=$$!; \
		sleep 5; \
		mvn test -Dtest=AcceptanceTest.LaunchCommandTest; \
		mvn test -Dtest=AcceptanceTest.LookCommandTest; \
		mvn test -Dtest=AcceptanceTest.StateCommandTest; \
		pkill -f "java.*libs/reference-server-0.1.0.jar" &

# Reference Test Iteration 2
reference-test-Itr2:
	@echo "Running Iteration 2 Reference Server Jar File"
	@java -jar libs/reference-server-0.2.3.jar -s 2 & \
		server_pid=$$!; \
		sleep 5; \
		mvn test -Dtest=AcceptanceTest.ForwardCommandTest; \
#		mvn test -Dtest=AcceptanceTest.LaunchRobotTest2x2; \

		pkill -f "java.*libs/reference-server-0.2.3.jar" &

# whitman Test Iteration 1
whitman-test-Itr1:
	@echo "Running Iteration 1 Server Jar File"
	@java -jar libs/robotworlds.jar & -s 2 &\
		server_pid=$$!; \
		sleep 5; \
		mvn test -Dtest=AcceptanceTest.LaunchRobotTest; \
		mvn test -Dtest=AcceptanceTest.LookCommandTest; \
		mvn test -Dtest=AcceptanceTest.StateCommandTest; \
		pkill -f "-jar libs/robotworlds.jar " &

# whitman Test Iteration 2
whitman-test-Itr2:
	@echo "Running Server Jar File"
	@java -jar libs/robotworlds.jar & -s 2 &\
		server_pid=$$!; \
		sleep 5; \
		mvn test -Dtest=AcceptanceTest.ForwardCommandTest; \
		mvn test -Dtest=AcceptanceTest.LaunchRobotTest2x2; \
		mvn test -Dtest=AcceptanceTest.WorldConfigTest; \
		pkill -f "-jar libs/robotworlds.jar " &

# Re-compress
re-compress:
	@echo "Recompressing Server Jar file with dependencies"
	@rm -rf libs/robotworlds.jar
	@mvn clean package -DskipTests
	@#mv target/robot_worlds-1.0-SNAPSHOT-jar-with-dependencies.jar libs/

.PHONY: acceptance_test
# use make all to run

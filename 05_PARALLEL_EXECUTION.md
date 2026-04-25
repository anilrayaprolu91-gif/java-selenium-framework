# Parallel Test Execution

This guide explains how to run tests in parallel using TestNG and Maven for faster execution.

## Running Tests in Parallel

- Run tests in parallel using the parallel TestNG configuration:
  ```bash
  mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
  ```
- The `testng-parallel.xml` file is configured for parallel method execution with 3 threads.

## Parallel Execution Options

- Parallel by methods (default):
  ```xml
  <suite ... parallel="methods" thread-count="3">
  ```
- Parallel by classes:
  ```xml
  <suite ... parallel="classes" thread-count="2">
  ```
- Parallel by instances:
  ```xml
  <suite ... parallel="instances" thread-count="4">
  ```
- Parallel by suite:
  ```xml
  <suite ... parallel="suite" thread-count="2">
  ```

## Configuration Details

- `testng-parallel.xml` uses:
  - `parallel="methods"` for method-level parallelism
  - `thread-count="3"` for 3 concurrent threads
  - `verbose="2"` for detailed logging

## Performance

- Sequential execution (testng.xml): ~50 seconds for 3 tests
- Parallel execution (testng-parallel.xml): ~20 seconds for 3 tests
- Adjust `thread-count` for more or fewer concurrent tests

## Thread Safety

- Each test gets its own WebDriver instance
- No shared state between tests
- Resource cleanup is handled per thread

## Customizing Parallel Execution

- To increase threads, create a config like:
  ```xml
  <suite ... thread-count="5">
  ```
- To decrease threads, use:
  ```xml
  <suite ... thread-count="2">
  ```

## File Structure

- `src/test/resources/testng-parallel.xml`: Parallel execution config
- `testng.xml`: Sequential execution config
- `pom.xml`: Maven configuration

## Recommended Usage

- Local development: Sequential (`testng.xml`)
- CI/CD: Parallel (`testng-parallel.xml`)
- Large suites: Create configs with higher thread counts

## Troubleshooting

- Ensure no shared state between tests
- Monitor resource usage (memory, CPU, browser instances)
- Adjust timeouts if tests run slowly in parallel

## Next Steps

- Run: `mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml`
- Observe: All tests run simultaneously
- Check: Test results in reports

---

Next: [06_ARCHITECTURE_GUIDE.md](./06_ARCHITECTURE_GUIDE.md)

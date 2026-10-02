### AI Agents Best Practices for Code Generation

This document outlines the best practices for generating code within this repository using AI agents.

#### 1. Contextual Awareness
*   **Provide System Context:** When asking for changes, ensure the agent understands the microservices architecture used here (Spring Boot, Hazelcast, MySQL, Maven).
*   **Reference Existing Patterns:** Direct the agent to look at existing implementations in modules like `person-service` or `employee-service` to maintain consistency in coding style, error handling, and documentation.

#### 2. Prompting Techniques
*   **Be Specific:** Instead of "add a new endpoint," use "add a GET endpoint to `PersonController` that returns a list of persons by last name."
*   **Iterative Development:** For complex features, break down the request into smaller, manageable steps (e.g., model -> repository -> service -> controller).
*   **Request Tests:** Always ask the agent to generate corresponding unit or integration tests (using JUnit 5 and Testcontainers as seen in the project).

#### 3. Code Quality and Style
*   **Maintain Consistency:** Ensure the generated code follows the existing Java 21 features and Spring Boot conventions.
*   **Review Generated Code:** Always review the AI-generated code for security vulnerabilities, performance issues, and adherence to project-specific rules.

#### 4. Critical Restriction: Target Directory
*   **Do Not Commit `target/`:** Under no circumstances should the `target` directory (or any of its contents) be added or committed to the repository. The `target` directory contains build artifacts that are generated locally and should be ignored by Git.

#### 5. Verification
*   **Verify Build:** After applying changes suggested by the agent, always run `mvn clean compile` to ensure the project still builds correctly.

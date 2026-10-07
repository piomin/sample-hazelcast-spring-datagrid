# Dependency Updates - Testcontainers v2 & Latest Versions

## Date: 2026-03-25

### Summary
Updated all dependencies to latest versions, migrated to Testcontainers v2 with proper artifact naming conventions.

## Dependency Changes

### Testcontainers (v1.21.4 → v2.0.4)
- **Core version**: 2.0.4
- **Migration**: Updated artifact IDs to use new `testcontainers-*` prefix
  - `org.testcontainers:mysql` → `org.testcontainers:testcontainers-mysql`
  - `org.testcontainers:junit-jupiter` → `org.testcontainers:testcontainers-junit-jupiter`
- **Version management**: Centralized in parent POM via `testcontainers.version` property
- **BOM**: Imported `testcontainers-bom` in parent `dependencyManagement` section

### Hazelcast (5.5.0 → 5.6.0)
- Updated to latest stable version: 5.6.0
- Includes both `hazelcast` and `hazelcast-spring` artifacts
- Version managed centrally via `hazelcast.version` property

### JaCoCo (0.8.14)
- Already at latest version (no change needed)
- Supports Java 25 officially

## Configuration Changes

### Parent POM (`pom.xml`)
Added centralized dependency management:

```xml
<properties>
    <hazelcast.version>5.6.0</hazelcast.version>
    <testcontainers.version>2.0.4</testcontainers.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>testcontainers-bom</artifactId>
            <version>${testcontainers.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>com.hazelcast</groupId>
            <artifactId>hazelcast</artifactId>
            <version>${hazelcast.version}</version>
        </dependency>
        <dependency>
            <groupId>com.hazelcast</groupId>
            <artifactId>hazelcast-spring</artifactId>
            <version>${hazelcast.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Module POMs
- Updated Testcontainers artifact IDs in `person-service/pom.xml` and `employee-service/pom.xml`
- All modules now use `${testcontainers.version}` property

## Testcontainers v2 Migration Notes

### Breaking Changes in v2.0
1. **Artifact naming**: All modules now use `testcontainers-` prefix
2. **JUnit 4 support removed**: Only JUnit 5 (Jupiter) supported
3. **Module names**: Standardized across all Testcontainers modules

### Benefits
- Improved consistency across modules
- Better module system support
- Cleaner dependency graph

## Verification

- ✅ Compilation: **SUCCESS**
- ⚠️  Tests: Require Docker disk space cleanup (environmental issue)
  - Error: "No space left on device" in Docker containers
  - Resolution: Run `docker system prune` to free up space

## Next Steps

To run tests successfully:
1. Clean up Docker: `docker system prune -a`
2. Verify Docker Desktop has sufficient allocated storage
3. Run: `mvn clean test`

## References
- [Testcontainers v2.0 Release Notes](https://github.com/testcontainers/testcontainers-java/releases/tag/2.0.0)
- [Hazelcast 5.6.0 Release Notes](https://docs.hazelcast.com/hazelcast/5.6/release-notes/5-6-0)

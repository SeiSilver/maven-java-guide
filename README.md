# Maven Guide for Java Development

## Introduction

Maven is a powerful build automation and project management tool specifically designed for Java projects. It provides a structured and efficient approach to managing dependencies, building projects, and generating artifacts. This guide will help you understand the key concepts and best practices for using Maven in Java development.

## Table of Contents

1. [Installation](#installation)
2. [Project Structure](#project-structure)
3. [POM (Project Object Model)](#pom-project-object-model)
4. [Dependencies Management](#dependencies-management)
5. [Build Lifecycle](#build-lifecycle)
6. [Plugins and Goals](#plugins-and-goals)
7. [Profiles](#profiles)
8. [Maven Repository](#maven-repository)
9. [Command Line Usage](#command-line-usage)
10. [Best Practices](#best-practices)

## Installation

Before getting started with Maven, make sure you have the following prerequisites:

- Java Development Kit (JDK) installed
- Maven installed (Download from https://maven.apache.org/download.cgi)

## Project Structure

The structure of most Maven projects will bear a resemblance to this.

```
myproject
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── App.java
│   │   └── resources
│   └── test
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── AppTest.java
│       └── resources
└── pom.xml
```

- `src/main/java`: Contains the main source code.
- `src/main/resources`: Contains resources used by the main source code.
- `src/test/java`: Contains test source code.
- `src/test/resources`: Contains resources used by the test source code.
- `pom.xml`: The project's configuration file.

## POM (Project Object Model)

The POM is an XML file named pom.xml that acts as the central configuration file for your Maven project. It contains essential project information, such as dependencies, build settings, and plugins. The POM is crucial because it:

- `Configures project`: The POM defines project details like its group and artifact IDs, version, and packaging type.
- `Manages dependencies`: You declare dependencies in the POM, and Maven automatically handles their resolution and download.
- `Orchestrates the build`: The POM specifies plugins and goals for building, testing, and packaging your project.
- `Promotes consistency`: With a common POM structure, teams can ensure consistent project configurations and facilitate collaboration.
- `Integrates with IDEs`: IDEs can import Maven projects using the POM, providing features like automatic dependency management and build support.

### Basic POM structure

```xml
<project>
  <modelVersion>...</modelVersion>
  <groupId>...</groupId>
  <artifactId>...</artifactId>
  <version>...</version>
  <packaging>...</packaging>
  <!-- Other elements -->
</project>
```

- `<modelVersion>`: Specifies the version of the POM schema being used. It ensures compatibility with different versions of Maven.
- `<groupId>`: Identifies the group or organization to which the project belongs. It helps to uniquely identify your project in the repository.
- `<artifactId>`: Provides a unique identifier for your project. It represents the name of the artifact (JAR, WAR, etc.) that is produced by the project.
- `<version>`: Specifies the version of your project. It helps to differentiate between different releases or iterations of your project.
- `<packaging>`: Specifies the type of artifact that the project produces, such as jar, war, pom, etc.

## Dependencies Management

In Maven, you manage dependencies by declaring them in the project's `pom.xml` file. Dependencies are external libraries or modules required by your project. Maven resolves and downloads these dependencies automatically from remote repositories.

- Declare dependencies within the `<dependencies>` section of the POM.
- Specify the group ID, artifact ID, and version of each dependency.
- Use dependency scopes to define when and where dependencies are used (e.g., `compile`, `test`, `provided`, `runtime`).
- Maven handles transitive dependencies, automatically including dependencies required by your declared dependencies.
- Conflict resolution is based on a "nearest-wins" strategy, but you can override versions if needed.
- Additional repositories can be configured in the POM or `settings.xml` if required dependencies are not found in the default repositories.
- Dependency management can be centralized in a parent POM to avoid duplicating declarations in multiple projects.

Maven simplifies dependency management by streamlining the process of declaring, resolving, and including external libraries in your project.

### Dependencies Structure

The `<dependency>` tag is used to specify a dependency in the `pom.xml` file of your Maven project. It contains information about the external library or module that your project depends on.

The basic structure of the `<dependency>` tag is as follows:

```xml
<dependency>
  <groupId>...</groupId>
  <artifactId>...</artifactId>
  <version>...</version>
  <scope>...</scope>
  <!-- Other optional elements -->
</dependency>
```

- `<groupId>`: Specifies the group or organization that is responsible for the dependency. It helps to uniquely identify the dependency among other libraries.
- `<artifactId>`: Provides the unique identifier for the dependency. It represents the name of the library or module that you want to include in your project.
- `<version>`: Specifies the version of the dependency. It helps to differentiate between different releases or versions of the library.
- `<scope>`: Defines the scope of the dependency, determining when and where it should be used. Common scopes include compile, test, provided, and runtime. The scope element is optional, and if not specified, the default scope is compile.

Example:

```xml
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-lang3</artifactId>
  <version>3.12.0</version>
  <scope>compile</scope>
</dependency>
```

By specifying dependencies in the `<dependencies>` section of your pom.xml file, Maven will automatically resolve and download the necessary libraries from remote repositories, ensuring that your project has access to the required dependencies.

> Scope: Choose the correct scope for each dependency based on its purpose and usage in your project. Use the following guidelines:
```text
- Use `compile` scope for dependencies that are required for both compilation and runtime.
- Use `provided` scope for dependencies provided by the runtime environment.
- Use `runtime` scope for dependencies needed during runtime but not during compilation.
- Use `test` scope for dependencies used only for testing purposes.
```

## Build Lifecycle

The Build Lifecycle in Maven represents a series of phases that define the process of building and packaging a project. It consists of three main lifecycles: `clean`, `default`, and `site`.

- **Clean Lifecycle**: The `clean` lifecycle removes any artifacts or files generated from previous builds. It ensures a clean state before starting a new build. Example: `clean`
- **Default Lifecycle**: The `default` lifecycle is used for building a project. It includes the following phases:

  - **Validate**: Validates the project structure and configuration.
  - **Compile**: Compiles the source code of the project.
  - **Test**: Runs tests against the compiled source code.
  - **Package**: Packages the compiled code into a distributable format (e.g., JAR or WAR).
  - **Install**: Installs the packaged artifact into the local repository.
  - **Deploy**: Deploys the artifact to a remote repository or server.

  Example sequence: `validate -> compile -> test -> package -> install -> deploy`
- **Site Lifecycle**: The `site` lifecycle generates project documentation and reports. It includes the following phases:

  - **Pre-Site**: Performs pre-site processing tasks.
  - **Site**: Generates project documentation and reports.
  - **Post-Site**: Performs post-site processing tasks.
  - **Site-Deploy**: Deploys the generated site to a remote location.

  Example sequence: `pre-site -> site -> post-site -> site-deploy`

In the default lifecycle, two important phases are:

- **Install**: The `install` phase installs the packaged artifact into the local repository, making it available for other projects or modules. This phase is commonly used during development and testing stages.
- **Package**: The `package` phase packages the compiled code into a distributable format (e.g., JAR or WAR). It prepares the artifact for distribution or deployment to different environments. This phase does not install the artifact into the local repository like the `install` phase.

The Build Lifecycle provides a standardized and consistent approach to building projects with Maven, automating common build tasks. Developers can customize and extend the lifecycle by configuring plugins and binding them to specific phases as needed.

NOTE:
- In Maven, running `mvn install` compiles the code, runs tests, packages the project, and installs the artifact into the local repository.
- On the other hand, `mvn clean install` performs a clean build by removing previous build artifacts before executing the remaining phases.
- The `clean` phase ensures a fresh build without any interference from previous artifacts.
- Running `mvn install` assumes previous artifacts are valid, saving time during subsequent builds.
- Choose `mvn clean install` when you want a clean build or have made changes to project configuration, dependencies, or build scripts.
- Choose `mvn install` when you know previous artifacts are up to date and do not need to be cleaned or rebuilt.

## Plugins and Goals

- Plugins and goals are essential components in Maven that allow you to extend its functionality.
- Plugins are responsible for executing specific tasks or operations, and goals represent individual tasks within a plugin.
- Plugins can be configured in the project's pom.xml file, and each plugin can have one or more goals associated with it.
- These goals can be executed through the Maven command line to perform various actions, such as generating reports, running tests, deploying artifacts, or executing custom scripts.

Example:

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
    <excludes>
      <exclude>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
      </exclude>
    </excludes>
    <excludeDevtools>true</excludeDevtools>
  </configuration>
</plugin>
```

In this example, the `spring-boot-maven-plugin` is defined within the `<plugins>` section of the `pom.xml` file. Let's break down its structure:

- `<groupId>`: Specifies the group ID of the plugin. In this case, the plugin is provided by the Spring Boot team and belongs to the `org.springframework.boot` group.
- `<artifactId>`: Indicates the artifact ID of the plugin. The artifact ID for the Spring Boot Maven plugin is `spring-boot-maven-plugin`.
- `<configuration>`: This section allows you to customize the behavior of the plugin by providing configuration options.
- `<excludes>`: Within the configuration, the `<excludes>` tag specifies a list of dependencies to exclude from the Spring Boot packaging. In this case, the `lombok` library from the `org.projectlombok` group is excluded.
- `<excludeDevtools>`: This element is used to exclude the Spring Boot devtools from the packaging process. It is set to `true`, indicating that devtools should be excluded.

By configuring the `spring-boot-maven-plugin` in this way, you can tailor the behavior of the plugin to suit your project's needs. In this specific example, it excludes the `lombok` library and devtools from the final packaged artifact.

Remember, the structure and configuration options of a plugin may vary depending on the plugin itself. It's important to refer to the plugin's documentation for specific details on how to configure and use it effectively in your Maven project.

## Maven Repository

The Maven Repository is a storage location for dependencies, plugins, and artifacts used in Maven projects. It has two types: the local repository on your machine and remote repositories hosted on servers.

### Local Repository

It's a cache of downloaded dependencies on your machine, stored in the following locations:

- Windows: `C:\Users\YourUsername\.m2`
- Linux: `/home/YourUsername/.m2`
- macOS: `/Users/YourUsername/.m2`

The local repository saves time by reusing dependencies across builds.
> Note: when you run `mvn install`, all the dependencies will also be stored in this local folder.

### Remote Repository

1. These repositories contain a wide range of libraries and plugins.
2. Maven retrieves dependencies from remote repositories when not available locally. Maven Central Repository is the default, but you can add more in the `pom.xml` file.

Example for `pom.xml`:

```xml
<project>
  <!-- ...other project configuration... -->

  <repositories>
    <repository>
      <id>my-remote-repo</id>
      <url>https://example.com/repository</url>
    </repository>
  </repositories>

  <!-- ...other project configuration... -->
</project>
```

- `<id>`: Specifies an ID for the repository. This can be any unique identifier you choose.
- `<url>`: Specifies the URL of the remote repository. Replace `https://example.com/repository` with the actual URL of the remote repository you want to use.

By adding the `<repository>` element with the appropriate `<id>` and `<url>`, Maven will be able to fetch dependencies from the specified remote repository in addition to the default Maven Central Repository.

Remember to replace `my-remote-repo` with a meaningful ID and `https://example.com/repository` with the actual URL of the remote repository you want to use.

Note: Ensure that the remote repository you are adding is accessible and contains the necessary dependencies or artifacts needed for your project.

> Maven uses the group ID, artifact ID, and version to search for artifacts in the repository.
> By leveraging the Maven Repository, you can easily manage dependencies without manually downloading and including each library or plugin.
> Ensure your project has access to the required repositories to resolve dependencies and plugins.

## Profiles

- Profiles allow you to define different build configurations for your Maven project. 
- They help you customize the build process based on different environments or requirements. 
- You can read more about profiles in the [Maven documentation](https://maven.apache.org/guides/introduction/introduction-to-profiles.html).

## Command Line Usage

- The Maven command line provides various options and commands to build, test, package, and manage projects. 
- It allows you to execute specific goals, skip phases, set properties, and more. 
- You can explore the command line usage in the [Maven documentation](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html#running-maven-commands).

## Best Practices

1. Use a consistent directory structure: Follow Maven's standard directory layout, organizing your project's source code, resources, and configuration files predictably.
2. Declare and manage dependencies: Specify all dependencies in your project's `pom.xml` file, including their versions. Use appropriate scopes (compile, provided, runtime, or test) for each dependency.
3. Use Maven plugins: Automate tasks like code compilation, test execution, documentation generation, and packaging using Maven plugins. Explore popular plugins such as `maven-compiler-plugin`, `surefire-plugin`, and `assembly-plugin`.
4. Follow the Maven lifecycle: Understand and adhere to the Maven build lifecycle with phases like `compile`, `test`, and `package`. Bind plugins and custom goals to the appropriate lifecycle phases for consistent and reproducible builds.
5. Customize your build: Configure plugins, specify additional resources, and override default behavior to customize your Maven build. Use the `<build>` and `<plugins>` sections in `pom.xml` for defining custom settings.
6. Use profiles for environment-specific configurations: Manage environment-specific configurations (e.g., database connections, runtime parameters) using Maven profiles. Activate specific profiles based on the target environment during the build.
7. Utilize Maven Central Repository: Rely on Maven Central Repository as the primary source for dependencies. Limit external repositories to necessary cases and ensure their reliability and maintenance.
8. Continuous Integration (CI) and build automation: Integrate Maven with a CI system (e.g., Jenkins, Travis CI) to automate the build, testing, and deployment processes. Configure the CI pipeline to trigger builds on code changes and execute Maven goals.

### Acknowledgments

[Getting Started with Maven](https://maven.apache.org/guides/index.html).

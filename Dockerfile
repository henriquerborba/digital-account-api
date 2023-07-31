# syntax=docker/dockerfile:1.2
FROM ibm-semeru-runtimes:open-20-jdk-jammy AS BUILDER

WORKDIR /tmp/build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN --mount=type=cache,target=/root/.m2/ \
    # FIX executable on windows
    chmod +x ./mvnw \
    && sed -i 's/\r$//' ./mvnw \
    # Download all dependencies
    && ./mvnw -B -C -e de.qaware.maven:go-offline-maven-plugin:resolve-dependencies

COPY src ./src
ARG EXTRA_BUILD_ARGS
RUN --mount=type=cache,target=/root/.m2/ \
    ./mvnw -B package ${EXTRA_BUILD_ARGS}


FROM ibm-semeru-runtimes:open-20-jre-jammy AS RUNNER

RUN groupadd -r api \
    && useradd --no-log-init -mr -g api api

USER api

COPY --from=BUILDER /tmp/build/target/*.jar api.jar

EXPOSE 9090

ENV SERVER_CONTEXT_PATH="/"

CMD ["java", "-Xtune:virtualized", "-jar", "/api.jar"]

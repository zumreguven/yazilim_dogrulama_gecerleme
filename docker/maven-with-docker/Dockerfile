FROM maven:3.9.9-eclipse-temurin-17

# Install docker CLI (docker.io package)
USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends docker.io ca-certificates curl gnupg2 \
    && rm -rf /var/lib/apt/lists/*

# Ensure docker CLI is available in PATH
ENV PATH="/usr/bin:${PATH}"


# Kendi scriptlerimizi kopyala
COPY ../../scripts/ /workspace/scripts/

# Keep the same working dir and user as the base image
WORKDIR /workspace

CMD ["tail", "-f", "/dev/null"]

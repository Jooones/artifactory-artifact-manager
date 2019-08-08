package com.jooones.artifactory.artifact.manager.matching;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public abstract class VersionMatch {

    private String artifactName;
    private String version;

    public VersionMatch(String artifactName, String version) {
        validateArguments(artifactName, version);
        this.artifactName = artifactName;
        this.version = version;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public Set<String> getMatchingVersions(Set<String> actualVersions) {
        return actualVersions.stream()
                .filter(actualVersion -> matches(actualVersion, version))
                .collect(toSet());
    }

    protected abstract boolean matches(String actualVersion, String version);

    private void validateArguments(String artifactName, String version) {
        if (StringUtils.isEmpty(artifactName) || StringUtils.isEmpty(version)) {
            throw new IllegalArgumentException("Required information missing [artifactName=" + artifactName + "] - [version=" + version + "]");
        }
    }

    @Override
    public String toString() {
        return "VersionMatch{" +
                "artifactName='" + artifactName + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

}

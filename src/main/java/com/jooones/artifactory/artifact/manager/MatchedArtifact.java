package com.jooones.artifactory.artifact.manager;

import com.jooones.artifactory.artifact.manager.matching.VersionMatch;

import java.util.Set;

public class MatchedArtifact {

    private VersionMatch versionMatch;
    private Set<String> versions;

    public MatchedArtifact(VersionMatch versionMatch, Set<String> versions) {
        this.versionMatch = versionMatch;
        this.versions = versions;
    }

    public VersionMatch getVersionMatch() {
        return versionMatch;
    }

    public Set<String> getVersions() {
        return versions;
    }

    @Override
    public String toString() {
        return "MatchedArtifact{" +
                "versionMatch=" + versionMatch +
                ", versions=" + versions +
                '}';
    }
}

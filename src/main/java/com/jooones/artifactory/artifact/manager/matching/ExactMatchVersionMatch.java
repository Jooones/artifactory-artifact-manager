package com.jooones.artifactory.artifact.manager.matching;

public class ExactMatchVersionMatch extends VersionMatch {

    public ExactMatchVersionMatch(String projectName, String versionToMatch) {
        super(projectName, versionToMatch);
    }

    @Override
    protected boolean matches(String actualVersion, String version) {
        return actualVersion.equals(version);
    }

    @Override
    public String toString() {
        return "exact-match -> " + super.toString();
    }
}

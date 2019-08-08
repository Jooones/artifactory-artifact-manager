package com.jooones.artifactory.artifact.manager.matching;

public class StartsWithVersionMatch extends VersionMatch {

    public StartsWithVersionMatch(String projectName, String versionToMatch) {
        super(projectName, versionToMatch);
    }

    @Override
    protected boolean matches(String actualVersion, String version) {
        return actualVersion.startsWith(version);
    }

    @Override
    public String toString() {
        return "starts-with -> " + super.toString();
    }
}

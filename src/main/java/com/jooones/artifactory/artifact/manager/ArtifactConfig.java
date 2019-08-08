package com.jooones.artifactory.artifact.manager;

import com.jooones.artifactory.artifact.manager.matching.ExactMatchVersionMatch;
import com.jooones.artifactory.artifact.manager.matching.StartsWithVersionMatch;
import com.jooones.artifactory.artifact.manager.matching.VersionMatch;

import java.util.HashSet;
import java.util.Set;

public class ArtifactConfig {

    private Set<VersionMatch> artifactsToDelete = new HashSet<>();

    /**
     * Configure the artifacts you want to delete here.<br/>
     * There are two ways to configure them:<br/>
     * - Exact-matching:<br/>
     * new ExactMatchVersionMatch("artifact-x", "0.1.0") will match only version "0.1.0"<br/>
     * - Starts-with-matching:<br/>
     * new StartsWithVersionMatch("artifact-y", "0.1") will match versions "0.1.0", "0.1.1", "0.11.0", "0.12.0"...<br/>
     * <br/>
     * <b>Important! Make sure you understand how the 'starts-with' variant works.
     * It doesn't interpret the numbers, it's text based. Meaning "0.1" will also delete "0.11.0"!
     * If you want to delete everything of version "0.1.xx", use 'starts-with' in combination with "0.1."</b>
     */
    public ArtifactConfig() {
         artifactsToDelete.add(new ExactMatchVersionMatch("artifact-x", "0.1.0"));
         artifactsToDelete.add(new StartsWithVersionMatch("artifact-y", "0.1."));
    }

    public Set<VersionMatch> getArtifactsToDelete() {
        return artifactsToDelete;
    }
}

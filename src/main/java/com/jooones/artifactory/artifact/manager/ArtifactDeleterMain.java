package com.jooones.artifactory.artifact.manager;

import java.time.Instant;

import static java.time.Duration.between;
import static java.time.Instant.now;

public class ArtifactDeleterMain {

    /**
     * How do I use this tool?<br/>
     * 1. Configure your Artifactory properties in an artifactory.properties file in the root of this project (see artifactory.properties.template)<br/>
     * 2. Define the artifacts you want to delete in {@link ArtifactConfig}<br/>
     * 3. Run this main method
     */
    public static void main(String[] args) {
        Instant startTime = now();
        new Deleter().findAndDelete(new ArtifactConfig().getArtifactsToDelete());
        System.out.println("Elapsed time (including user confirmation time): " + between(startTime, now()).getSeconds() + " seconds");
    }

}

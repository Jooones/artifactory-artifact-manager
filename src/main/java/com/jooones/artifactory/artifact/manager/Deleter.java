package com.jooones.artifactory.artifact.manager;

import com.jooones.artifactory.artifact.manager.matching.VersionMatch;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.jooones.artifactory.artifact.manager.ArtifactoryPropertyReader.readProperty;

public class Deleter {

    private static final String ARTIFACTORY_USERNAME = readProperty("username");
    private static final String ARTIFACTORY_PASSWORD = readProperty("password");
    private static final String ARTIFACTORY_URL = readProperty("url");

    public void delete(VersionMatch versionMatch) {
        Set<String> versions = getMatchingVersions(versionMatch);
        if (!versions.isEmpty()) {
            delete(versions, versionMatch.getArtifactName());
        } else {
            System.out.println("No actual versions found for " + versionMatch);
        }
    }

    private Set<String> getMatchingVersions(VersionMatch versionMatch) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(ARTIFACTORY_URL + versionMatch.getArtifactName() + "?list&listFolders=1")
                    .basicAuth(ARTIFACTORY_USERNAME, ARTIFACTORY_PASSWORD)
                    .asJson();
            return versionMatch.getMatchingVersions(getActualVersions(response));
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getActualVersions(HttpResponse<JsonNode> response) {
        Set<String> versions = new HashSet<>();
        response.getBody()
                .getObject()
                .getJSONArray("files")
                .forEach(o -> versions.add(((String) ((JSONObject) o).get("uri")).substring(1)));
        return versions;
    }

    private void delete(Set<String> versions, String artifactName) {
        if (userSaysGo(artifactName, versions)) {
            versions.forEach(version -> deleteVersion(artifactName, version));
        } else {
            System.out.println("Cancelled by user.");
        }
    }

    private boolean userSaysGo(String artifactName, Set<String> versions) {
        System.out.println("Are you sure you want to delete the following artifacts? ");
        versions.forEach(version -> System.out.println(artifactName + " " + version));
        return "Y".equals(getUserInput());
    }

    private String getUserInput() {
        System.out.println("Awaiting confirmation (Y)..");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private void deleteVersion(String artifactName, String version) {
        try {
            HttpResponse<String> response = Unirest.delete(removeInfixFromUrl(ARTIFACTORY_URL) + artifactName + "/" + version  + "/")
                    .basicAuth(ARTIFACTORY_USERNAME, ARTIFACTORY_PASSWORD)
                    .asString();
            System.out.println("Artifact " + artifactName + ":" + version + " deletion response code = " + response.getStatus());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private String removeInfixFromUrl(String artifactoryUrl) {
        return artifactoryUrl.replace("/api/storage", "");
    }
}

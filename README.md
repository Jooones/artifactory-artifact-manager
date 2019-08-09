# Artifactory artifact manager
Java tool which uses the Artifactory API to delete artifacts in bulk from the configured Artifactory.

[![GitHub license](https://img.shields.io/github/license/Jooones/artifactory-artifact-manager.svg)](https://github.com/Jooones/artifactory-artifact-manager/blob/master/LICENSE)

## Why?
Because Artifactory doesn't provide an easy way to delete old artifacts in bulk (at least not at the time of writing).
With the Artifactory UI you can delete artifacts either one by one or a few at a time. 
As this can be a very tedious job, I created my own tool to do it in bulk.

## Suggested way to use this tool
1. Clone this repository
1. Open the project in your favorite IDE
1. Copy the `artifactory.properties.template` file in the root directory and name it `artifactory.properties`
1. Fill in the `artifactory.properties` file with your Artifactory properties
1. Open `ArtifactConfig.java`, choose your matching strategy and configure the artifacts and corresponding versions you want to delete
1. Run the `main` method in `ArtifactDeleterMain.java`
1. Review the versions which will be deleted in the console output
1. Input `Y` + enter if you are satisfied

## Matching strategy
There are 2 ways to configure this tool to match the versions you want to delete:
- Exact-matching:  
`new ExactMatchVersionMatch("artifact-x", "0.1.0")` will match only version "0.1.0"

- Starts-with-matching:  
`new StartsWithVersionMatch("artifact-y", "0.1")` will match versions "0.1.0", "0.1.1", "0.11.0", "0.12.0"...

**Important! Make sure you understand how the _starts-with_ variant works. 
It doesn't interpret the numbers, it's text based. Meaning "0.1" will also delete "0.11.0"! 
If you want to delete everything of version "0.1.xx", use 'starts-with' in combination with "0.1."**


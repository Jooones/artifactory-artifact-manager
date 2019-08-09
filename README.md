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
1. Input `Y` and hit enter if you are satisfied

## Version matching strategy
There are 2 ways to configure this tool to match the versions you want to delete:
- Exact-matching:  
`new ExactMatchVersionMatch("artifact-x", "0.1.0")` will match only version "0.1.0"

- Starts-with-matching:  
`new StartsWithVersionMatch("artifact-y", "0.1")` will match versions "0.1.0", "0.1.1", "0.11.0", "0.12.0"...

**Important! Make sure you understand how the _starts-with_ variant works. 
It doesn't interpret the numbers, it's text based. Meaning "0.1" will also delete "0.11.0"! 
If you want to delete everything of version "0.1.xx", use 'starts-with' in combination with "0.1."**

## Authentication
The tool uses basic authentication to connect to the configured Artifactory's REST API.

## Example
Let's say we configure the following version matches:
```java
artifactsToDelete.add(new ExactMatchVersionMatch("artifact-x", "0.1.1"));
artifactsToDelete.add(new ExactMatchVersionMatch("artifact-x", "0.1.2"));
artifactsToDelete.add(new StartsWithVersionMatch("artifact-y", "0.2."));
```
When triggering the tool, the result could be as follows:
```
WARN: no actual versions found for: exact-match -> VersionMatch{artifactName='artifact-x', version='0.1.1'}
Are you sure you want to delete the following artifacts? 
artifact-x 0.1.2
artifact-y 0.2.0
artifact-y 0.2.1
artifact-y 0.2.2
Awaiting confirmation (Y)..
Y
Artifact artifact-x:0.1.2 deletion response code = 204
Artifact artifact-y:0.2.0 deletion response code = 204
Artifact artifact-y:0.2.1 deletion response code = 204
Artifact artifact-y:0.2.2 deletion response code = 204
Elapsed time (including user confirmation time): 12 seconds
```
How to interpret the output:
- First the tool warns you of versions which could not be found, this is just a warning so the tool will continue
- Then it shows you the actual versions it was able to match and asks for confirmation before executing the irreversible delete
- If you confirm, the tool will attempt to delete the artifacts and show the http response code for each deletion attempt
    - The expected response code is in the 200's
- Finally the tool displays how look it took to run the whole thing, including user confirmation time

## Aborting
Simply type something else than `Y` (`n` for instance) and hit enter.

```
Are you sure you want to delete the following artifacts? 
artifact-x 0.1.0
Awaiting confirmation (Y)..
n
Aborted by user.
Elapsed time (including user confirmation time): 10 seconds
```
Just killing the program will also abort the delete.

## Why Java?
Q: But Jooones, why write this tool in Java? This looks like a perfect tool to create using a scripting language and just provide a shell script for the end user to run.  
J: Well, I find it a lot easier to write unit tests for java code than code produced by scripting languages  
_starts sweating_  
_5 seconds go by_  
Q: Um.. I don't see any unit tests?  
J: ABORT! ABORT!

## "Your tool has potential but the final result sucks balls"
Feel free to fork, update and sell for a high price.  
I don't mind!  

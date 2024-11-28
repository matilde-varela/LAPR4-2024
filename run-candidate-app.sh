#!/usr/bin/env bash

# set the class  path,
# assumes the build was executed with maven copy-dependencies
export BASE_CP=jobs4u.app.candidate.console/target/jobs4u.app.candidate.console-0.1.0.jar:jobs4u.app.candidate.console/target/dependency/*;

# call the java VM, e.g,
java -cp $BASE_CP jobs4u.base.app.candidate.console.CandidateApp
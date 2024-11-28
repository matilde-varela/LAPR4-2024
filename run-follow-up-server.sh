#!/usr/bin/env bash

# set the class  path,
# assumes the build was executed with maven copy-dependencies
export BASE_CP=jobs4u.followup.server/target/jobs4u.followup.server-0.1.0.jar:jobs4u.followup.server/target/dependency/*;

# call the java VM, e.g,
java -cp $BASE_CP followup.server.FollowUpServer
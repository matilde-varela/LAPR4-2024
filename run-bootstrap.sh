#!/usr/bin/env bash

#REM set the class  path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=jobs4u.bootstrap/target/jobs4u.bootstrap-0.1.0.jar:jobs4u.bootstrap/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP jobs4u.base.app.bootstrap.BaseBootstrap
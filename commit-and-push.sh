#!/bin/sh
COMMIT_MESSAGE=$1
DO_JENKINS_BUILD_ENABLED=$2

git commit -am "$COMMIT_MESSAGE"
git push

#------------------------------------------------------------------------------------------------------------------------
if [ "$DO_JENKINS_BUILD_ENABLED" = "false" ] ; then
  exit 1;
fi

USER=ivan
TOKEN=1152347fd2957db1f021c69bc26400f7f8

CRUMB=$(curl --user ivan:1152347fd2957db1f021c69bc26400f7f8 http://localhost:8080/crumbIssuer/api/json | jq -r '.crumb')

curl -X POST http://localhost:8080/job/social_network/build?token=buildme -H "Jenkins-Crumb:"$CRUMB --user $USER:$TOKEN

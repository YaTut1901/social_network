git commit -am "$1"
git push

#------------------------------------------------------------------------------------------------------------------------
if [[ $2 == "true" ]] ; then
  exit
fi

USER=ivan
TOKEN=1152347fd2957db1f021c69bc26400f7f8

CRUMB=$(curl --user ivan:1152347fd2957db1f021c69bc26400f7f8 http://localhost:8080/crumbIssuer/api/json | jq -r '.crumb')

curl -X POST http://localhost:8080/job/social_network/build?token=buildme -H "Jenkins-Crumb:"$CRUMB --user $USER:$TOKEN

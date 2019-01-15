#!/bin/bash
docker stop recommender
docker run -it --rm -v maven-repo:/root/.m2 -v /home/student/biznes/recommender-system:/usr/src/mymaven -w /usr/src/mymaven maven mvn package
docker start recommender

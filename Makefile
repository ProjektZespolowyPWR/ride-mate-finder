run:
	cd ride-mate-finder-app/ && \
    mvn clean install && \
    docker build -t ride-mate-finder . && \
    docker run -p 7777:7777 ride-mate-finder --name app
clean:
	cd ride-mate-finder-app/ && \
    mvn clean
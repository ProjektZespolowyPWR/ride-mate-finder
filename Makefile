run:
	cd ride-mate-finder-app/ && \
    mvn clean install && \
    cd .. && \
    docker build -t ride-mate-finder . && \
    docker run -d -p 7777:7777 ride-mate-finder
clean:
	cd ride-mate-finder-app/ && \
    mvn clean
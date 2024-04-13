prepare:
	docker-compose up -d
	bash wait.sh
run:
	cd ride-mate-finder-app && \
	mvn clean install -DskipDocumentation=true -DskipTests && \
	docker build -t ride-mate-finder . && \
	docker run --rm -p 7777:7777 --network=ride-mate-finder_system --name app ride-mate-finder
clean:
	docker ps -a | grep app && docker stop app && docker rm app || true
	cd ride-mate-finder-app/ && \
	docker-compose down && \
    mvn clean
swagger:
	cd ride-mate-finder-app && \
	mvn clean install -DskipDocumentation=false -DskipTests
sql:
	cd ride-mate-finder-app && \
	mvn clean install -DskipTests -DgenerateEntriesFromDB=true && \
	rm src/main/java/com/ridematefinder/sql/FlywaySchemaHistory.java && \
	cd .. && \
	bash delete_date.sh
migrate:
	cd ride-mate-finder-app && \
    mvn spring-boot:run -Dconsole=true -Dspring-boot.run.profiles=console-application

default: docker-build

# App name
NAME = analizer2

# Binary name
EXEC = ./$(NAME)

###############
# DOCKER
###############

# Build docker image
docker-build:
	docker build --no-cache --rm --tag=$(NAME) .

# Start application on port 8080
docker-run:
	docker run -e NEO4J_HOST -e NEO4J_PORT -e NEO4J_USER -e NEO4J_PASS --publish 8080:8080 --rm --name=$(NAME) $(NAME) mvn exec:java

# Build and run
docker:
	make docker-build
	make docker-run

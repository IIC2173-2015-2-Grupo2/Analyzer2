# Analyzer2

[![Build Status](https://travis-ci.org/IIC2173-2015-2-Grupo2/Analyzer2.svg)](https://travis-ci.org/IIC2173-2015-2-Grupo2/Analyzer2) [![Codacy Badge](https://api.codacy.com/project/badge/9ce9e33fe5664ce6b8c5e44c0e5dfcac)](https://www.codacy.com/app/lopezjuripatricio/Analyzer2)

## Setup

### Environment vars
```sh
export NEO4J_HOST="192.168.99.100"
export NEO4J_PORT="7474"
export NEO4J_USER="neo4j"
export NEO4J_PASS="neo4j"
```

### Docker
This image exposes port `4567`.

#### Build
```sh
# Automatic
$ make

# Manual
$ docker build --no-cache --rm -tag==analizer2
```

#### Run
```sh
# Automatic
$ make docker-run

# Manual
$ docker run -e NEO4J_HOST -e NEO4J_PORT -e NEO4J_USER -e NEO4J_PASS --publish 4567:4567 --rm --name=analizer2 analizer2
```

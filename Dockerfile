FROM ubuntu:latest
LABEL authors="alexhrushko"

ENTRYPOINT ["top", "-b"]
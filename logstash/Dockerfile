ARG ELASTIC_VERSION

# https://www.docker.elastic.co/
FROM docker.elastic.co/logstash/logstash:${ELASTIC_VERSION:-8.17.1}

# Add your logstash plugins setup here
# Example: RUN logstash-plugin install logstash-filter-json
RUN curl -L --output /usr/share/logstash/logstash-core/lib/jars/postgresql.jar https://jdbc.postgresql.org/download/postgresql-42.7.1.jar
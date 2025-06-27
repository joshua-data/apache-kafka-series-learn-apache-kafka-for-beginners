
### 1. Go to Kafka directory.

```bash
cd /opt/homebrew/Cellar/kafka/4.0.0/
```

### 2. Create folders for Connectors.

```bash
mkdir connectors
cd connectors/
mkdir source-connector-wikimedia
cd source-connector-wikimedia/
```

### 3. Source Connector

```bash
cp ~/development/study-course-kafka/09-kafka-connect/wikimedia-source-connector/kafka-connect-wikimedia-1.0-all.jar .
```

### 4. Sink Connector

```bash
cd ..
cp -r ~/development/study-course-kafka/09-kafka-connect/elasticsearch-sink-connector .
```

### 5. Go to Kafka Binary directory.

```bash
cd ..
cd ..
cd bin/

```

### 6. Run `connect-standalone.properties` command for Source & Sink Connectors.

```bash
connect-standalone config/connect-standalone.properties config/wikimedia.properties config/elasticsearch.properties
```

### 7. Check if the Source Connector is now live!

```
http://localhost:8080/console/joshua-kafka-cluster-1/topics/wikimedia.recentchange.connect?tab=consume
```

### 8. Check if the Sink Connector is now live!

```
http://localhost:5601/app/dev_tools#/console

GET wikimedia.recentchange.connect/_search
{
    "query": {
        "match_all": {}
    }
}
```


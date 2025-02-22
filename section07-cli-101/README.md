# Contents

1. Intro to CLI
2. Kafka Topics CLI

---

# Intro to CLI

- They come bundled with the Kafka binaries.
- You should be able to invoke the CLI from anywhere on your computer.

```bash
kafka-topics
```

- Use the `--bootstrap-server` option everywhere, not `--zookeeper`.

```bash
# Yes!
kafka-topics --bootstrap-server localhost:9092
# No!
kafka-topics --zookeeper localhost:2181
```

# Kafka Topics CLI

### 1. Create Kafka Topics

- There's no topics at the moment.

```bash
kafka-topics --bootstrap-server localhost:9092 --list
```

- Let's create the first topic.

```bash
kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create
```

- Let's create the second topic.
    - partitions: 3

```bash
kafka-topics --bootstrap-server localhost:9092 --topic second_topic --create --partitions 3
```

- Let's create the third topic.
    - partitions: 3
    - replication factor: 2 -> 1

```bash
# This results in error because there's only 1 broker in my localhost.
    # ================
    # InvalidReplicationFactorException: Replication factor: 2 larger than available brokers: 1.
    # ================
kafka-topics --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 2
# Try this out instead!
kafka-topics --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 1
```

### 2. List Kafka Topics

```bash
kafka-topics --bootstrap-server localhost:9092 --list
```

### 3. Describe Kafka Topics

```bash
kafka-topics --bootstrap-server localhost:9092 --topic first_topic --describe
kafka-topics --bootstrap-server localhost:9092 --topic second_topic --describe
kafka-topics --bootstrap-server localhost:9092 --topic third_topic --describe
```

### 4. Delete a Kafka Topic

- It only works if `delete.topic.enable=true`.

```bash
kafka-topics --bootstrap-server localhost:9092 --topic first_topic --delete
```

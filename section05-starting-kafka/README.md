### **Download Kafka using `brew`**

- **Installation Guide**: [How to install Apache Kafka on Mac with Homebrew](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-mac-with-homebrew/)

1. **Install Kafka using `brew`.** (It will install Java JDK for you as well.)
    
```bash
brew install kafka
```
    
- **Kafka**: `/opt/homebrew/Cellar/kafka`
- **Binaries & Scripts**: `/opt/homebrew/bin`
- **Kafka Configurations**: `/opt/homebrew/etc/kafka`
- **Zookeeper Configurations**: `/opt/homebrew/etc/zookeeper`
- **The `log.dirs` config (the location for Kafka data)** will be set to `opt/homebrew/var/lib/kafka-logs`

2. **Run the command below to check if Kafka has been successfully installed.**
    
```bash
kafka-topics
```
    
3. **Start Zookeeper using the binaries.**

- Apache Kafka depends on Zookeeper for cluster management. Hence, **Zookeeper must be started before starting Kafka.**
- There’s no need to explicitly install Zookeeper, as it comes included with Apache Kafka.
- You can start Zookeeper using the following command.
        
```bash
/opt/homebrew/bin/zookeeper-server-start /opt/homebrew/etc/zookeeper/zoo.cfg
```
        
4. **Start Kafka using the binaries in another terminal.**

- Open another terminal, and run the following command from the root of Apache Kafka to start Apache Kafka.
    
```bash
/opt/homebrew/bin/kafka-server-start /opt/homebrew/etc/kafka/server.properties
```
    
- Ensure to keep both terminals open, otherwise, you’ll shut down Kafka or Zookeeper.
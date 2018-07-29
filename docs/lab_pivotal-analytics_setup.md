# Demo Setup

1. Setup elasticsearch (must be 5.6.4)

Create elasticsearch cluster
```
kubectl apply -f vsphere-volume-sc-fast.yaml
kubectl create namespace es-demo
kubectl config set-context my-cluster --namespace=es-demo
kubectl apply -f es-statefulset.yaml
kubectl apply -f es-svc.yaml
```
# Get service details
```
kubectl get nodes -o wide
kubectl get svc
```
```
export ES_HOST=10.193.152.238
export ES_PORT=32224
export ES_HTTP_PORT=32677
```

# Test elasticearch cluster
```
http $ES_HOST:$ES_HTTP_PORT
http POST $ES_HOST:$ES_HTTP_PORT/trader/trades symbol=PVTL
http $ES_HOST:$ES_HTTP_PORT/trader/_search
```

2. Build and deploy analytics service
need to provide IP and Port in the test resources in order to pass tests

3. Push Analytics Service to cloud foundry
verify by accessing service /actuator/health

9. Go get the database connection string

5. Build and deploy analytics-scdf-sink to artifactory
need to provide IP and Port in the test resources in order to pass tests

4. Setup SCDF

```
cf target -s bank
cf create-service p-dataflow standard data-flow -c '{"maven.remote-repositories.repo1.url": "https://dl.bintray.com/dpfeffer/maven-repo","maven.remote-repositories.repo1.auth.username": "dpfeffer","maven.remote-repositories.repo1.auth.password": "391d07842f9980aa99aa7b8af766b3c78b0bd068"}'
```

6. Access the dataflow shell
```
cf dataflow-shell data-flow
```

7. Import the starters
```
app import http://bit.ly/Darwin-SR1-stream-applications-rabbit-maven
```

8. Register the analytics-scdf-sink
```
app register --name analytics-scdf-sink --type sink --uri maven://io.pivotal.analtics:analytics-scdf-sink:1.1.4
```

10. Update the create string with connection info and create string

echo "stream create --name trader --definition \"jdbc --query='select * from orders where tag is NULL' --update='update orders set tag=''1'' where orderid in (:orderid)' --password=ff3yseipaekzoy8e --username=9daf34279e82461c860e5a15fe715e54 --url='jdbc:mysql://10.193.152.233:3306/service_instance_db' --spring.datasource.driver-class-name=org.mariadb.jdbc.Driver | analytics-scdf-sink --es-sink.cluster-name=elasticsearch --es-sink.host=$ES_HOST --es-sink.port=$ES_PORT\""
**copy output**
```
stream create --name trader2 --definition "jdbc --query='select * from orders where tag is NULL' --update='update orders set tag=''1'' where orderid in (:orderid)' --password=bqujkdet1g49667s --username=8cf2f8d0a0b54a759b7993db5c356355 --url='jdbc:mysql://10.193.138.237:3306/service_instance_db' --spring.datasource.driver-class-name=org.mariadb.jdbc.Driver | analytics-scdf-sink --es-sink.cluster-name=elasticsearch --es-sink.host=10.193.138.120 --es-sink.port=30432"
```

11. Deploy stream
```
stream deploy trader2
```

12. Create CUPS and Bind it to analytics-service
*Update pivotal-bank analytics-service with connection information*
--es-sink.host=10.193.138.120 --es-sink.port=30432"
```
cf target -s bank
cf create-user-provided-service es -p "'{\"cluster-name\":\"elasticsearch\",\"host\":\"10.193.138.120\",\"port\":\"30432\"}'"
cf bind-service analytics es
cf restage analytics
```

12. Run your test
Make a trade, then view the analytics

13. Un-deploy and un-register SCDF
within shell

```
stream undeploy trader
stream destroy trader
app unregister --name analytics-scdf-sink --type sink
```

14. Tear Down elasticsearch
```
kubectl delete -f es-statefulset.yaml
kubectl delete -f es-svc.yaml
kubectl delete -f vsphere-volume-sc-fast.yaml
```

# Do the Demo

# Create elasticsearch cluster
```
kubectl apply -f vsphere-volume-sc-fast.yaml
kubectl create namespace es-demo
kubectl config set-context my-cluster --namespace=es-demo
kubectl apply -f es-statefulset.yaml
kubectl apply -f es-svc.yaml
```

# Get service details
```
kubectl get nodes -o wide
kubectl get svc

export ES_HOST=10.193.152.238
export ES_PORT=32224
export ES_HTTP_PORT=32677
```

# Test elasticearch cluster
```
http $ES_HOST:$ES_HTTP_PORT
http POST $ES_HOST:$ES_HTTP_PORT/trader/trades symbol=PVTL
http $ES_HOST:$ES_HTTP_PORT/trader/_search
```

# Create SCDF
```
echo "stream create --name trader --definition \"jdbc --query='select * from orders where tag is NULL' --update='update orders set tag=''1'' where orderid in (:orderid)' --password=ff3yseipaekzoy8e --username=9daf34279e82461c860e5a15fe715e54 --url='jdbc:mysql://10.193.152.233:3306/service_instance_db' --spring.datasource.driver-class-name=org.mariadb.jdbc.Driver | analytics-scdf-sink --es-sink.cluster-name=elasticsearch --es-sink.host=$ES_HOST --es-sink.port=$ES_PORT\""
```
**copy output**

```
cf target -s pivotal-bank
cf dataflow-shell data-flow
```
** Create the stream by pasting command from above
```
stream deploy trader
```
**Note: this takes about 1 minute**
**Note: in another tab**
```
cf apps
```
** cf logs first app --recent

# Update pivotal-bank analytics-service with connection information
```
cf target -s pivotal-bank
cf create-user-provided-service es -p "'{\"cluster-name\":\"elasticsearch\",\"host\":\"$ES_HOST\",\"port\":\"$ES_PORT\"}'"
cf restage analytics
```

# Test it all out


# Clean up
```
kubect
```

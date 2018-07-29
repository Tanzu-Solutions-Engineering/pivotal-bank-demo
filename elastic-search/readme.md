# Background
This was adapted from https://medium.appbase.io/deploy-elasticsearch-with-kubernetes-on-aws-in-10-steps-7913b607abda. The changes were to add a persistent volumen claim for vspherevolumes and to utilize a different container image that uses elasticsearch 2.4.

# Deploy
```
kubectl apply -f vsphere-volume-sc-fast.yaml
kubectl apply -f es-statefulset.yaml
kubectl apply -f es-svc.yaml
```

# Test
Get the IP address and get the http and native ports
```
kubectl get nodes -o wide
export ES_HOST=<ANY_NODE_IP_ADDRESS>
kubectl get svc
export ES_HTTP_PORT=<PROXY_PORT_FROM_9200>
export ES_PORT=<PROXY_PORT_FROM_9300>
```
Access elastic search and see basic output
```
http $ES_HOST:$ES_HTTP_PORT
```
Index a document
```
http POST $ES_HOST:$ES_HTTP_PORT/trader/trades symbol=MS
```
Search the index
```
http $ES_HOST:$ES_HTTP_PORT/trader/_search
```

# Cleanup
```
kubectl delete -f es-statefulset.yaml
kubectl delete -f es-svc.yaml
kubectl delete -f vsphere-volume-sc-fast.yaml
```

# Run Locally
ELASTICSEARCH_CLUSTERNAME=elasticsearch ELASTICSEARCH_HOST=10.193.152.238 ELASTICSEARCH_PORT=32231 java -jar 

# Create CF User Provided Service

cf create-user-provided-service es -p '{"cluster-name":"elasticsearch","host":"10.193.152.236","port":"30141"}'
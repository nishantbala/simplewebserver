echo = $(curl -X POST \
  http://localhost:8080/server-1.0/ \
  -H 'cache-control: no-cache' \
  -H "Content-Type: text/plain" \
  -d end -s)
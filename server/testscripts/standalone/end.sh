echo = $(curl -X POST \
  http://localhost:1337/ \
  -H 'cache-control: no-cache' \
  -H "Content-Type: text/plain" \
  -d end -s)
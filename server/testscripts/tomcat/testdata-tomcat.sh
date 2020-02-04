#!/bin/bash
url="http://localhost:8080/server-1.0/"
data="1 2 3 4"
for d in $data; do
   echo "fetching $url for $d"
   echo $(curl -X POST \
          $url \
          -H 'cache-control: no-cache' \
          -H "Content-Type: text/plain" \
          -d $d -s) && echo "done-$d" &
done
wait
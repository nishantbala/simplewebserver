#!/bin/bash
url="http://localhost:1337/"
data="1 2 3 4"
for d in $data; do
   echo "fetching $url for $d"
   echo $(curl -X POST \
          http://localhost:1337/ \
          -H 'cache-control: no-cache' \
          -H "Content-Type: text/plain" \
          -d $d -s) && echo "done-$d" &
done
wait
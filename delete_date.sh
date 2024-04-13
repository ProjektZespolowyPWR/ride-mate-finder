#!/bin/bash

if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  find ride-mate-finder-app/src/main/java/com/ridematefinder/sql -type f -exec sed -i '' '2d' {} +
else
  # Linux
  find ride-mate-finder-app/src/main/java/com/ridematefinder/sql -type f -exec sed -i '2d' {} +
fi
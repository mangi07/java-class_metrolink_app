if [ -a "com.ben.metrolink.db" ]; then
  echo "wiping the existing database"
  rm com.ben.metrolink.db
fi
touch com.ben.metrolink.db
sqlite3 com.ben.metrolink.db < com.ben.metrolink.sql
sqlite3 com.ben.metrolink.db < import_raw.sql

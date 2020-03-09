
Tutorials: https://www.playframework.com/documentation/2.8.x/Tutorials

1. Create zip file from sbt console
dist
2. Copy to external directory
cp ./target/universal/play-java-seed-1.0-SNAPSHOT.zip ~/target-folder/

3. Generate secret
https://www.playframework.com/documentation/2.8.x/ApplicationSecret

4. Update command and Run one
~/myapp$ play-java-seed-1.0-SNAPSHOT/bin/play-java-seed -Dhttp.port=8000 -Dlogger.file=conf/logback.xml -Dplay.http.secret.key='your-secret-key'
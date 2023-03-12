rm -rf core-starter/target/
mvn clean install -DskipTests
docker build -t apps.horsecoder.com:5000/s-8f0e03dc2addd81e8a30b6446c9fddfd-core:latest -f ./Dockerfile.prod .
docker push apps.horsecoder.com:5000/s-8f0e03dc2addd81e8a30b6446c9fddfd-core:latest
curl -X POST "http://apps.horsecoder.com:32871/task" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"image\": \"apps.horsecoder.com:5000/s-8f0e03dc2addd81e8a30b6446c9fddfd-core:latest\", \"name\": \"8f0e03dc2addd81e8a30b6446c9fddfd-core\", \"namespace\": \"horsecoder-test\", \"path\": \"/core/\", \"port\": 43355}"

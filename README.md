## spring-boot-enable-https-ssl-self-signed-certificate

Purpose : Enable https port 8443 for the spring boot application. <br/>
Reason : Provide secure data transfer between rest api and client.  <br/>

### Local run steps <br/>
1- Generate self signed ssl certificate by using JDK keytool. <br/>
<pre>
D:\DEV\ssl>keytool -genkeypair -keypass password -storepass password -keystore serverkeystore -alias custapi -keyalg RSA -validity 365
What is your first and last name?
[Unknown]:
What is the name of your organizational unit?
[Unknown]:
What is the name of your organization?
[Unknown]:
What is the name of your City or Locality?
[Unknown]:
What is the name of your State or Province?
[Unknown]:
What is the two-letter country code for this unit?
[Unknown]:
Is CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown correct?
[no]:  yes
</pre>
1.1- Export public key from keystore. <br/>
<pre>
D:\DEV\ssl>keytool -export -alias custapi -keystore serverkeystore -storepass password -rfc -file custapi.crt
Certificate stored in file "custapi.crt"
</pre>
2- Move generated certificate file into your spring boot application resources directory named with "ssl". <br/>

3- Add following properties into application.properties file. <br/>
<pre>
server.ssl.key-store-type=PKCS12 <br/>
server.ssl.key-store=classpath:ssl/serverkeystore <br/>
server.ssl.key-store-password=password <br/>
server.ssl.key-alias=custapi <br/>
server.ssl.enabled=true <br/>
server.port=8443 <br/>
</pre>
4- Start Spring Boot API by running main method containing class CustomerInfoApplication.java in your IDE. <br/>
5- Alternatively you can start your Docker container by following the commands below. <br/>
NOT : Execute maven command from where the pom.xml is located in the project directory to create Spring Boot executable jar. <br/>
<pre> 
$ mvn clean install -U -X <br/>
</pre>
<pre>
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev
</pre>

In order to check the https ssl configuration swagger_ui can be accessed via https secure port : <br/>
https://localhost:8443/customer-info/swagger-ui/index.html <br/><br/>
![https_swagger_ui](doc/https_swagger_ui.png) <br/>

### Tech Stack
Java 11 <br/>
H2 Database Engine <br/>
spring boot <br/>
spring boot starter data jpa <br/>
spring boot starter web <br/>
spring boot starter test <br/>
springfox swagger ui <br/>
springdoc openapi ui <br/>
spring security web <br/>
hibernate <br/>
logback <br/>
maven <br/>
hikari connection pool <br/>
Docker <br/>
<br/>

### Docker build run steps
NOT : Execute docker commands from where the DockerFile is located. <br/>
NOT : Tested on Windows 10 with Docker Desktop Engine Version : 20.10.11 <br/>
<pre>
$ docker system prune -a --volumes <br/>
$ docker build . --tag demo  <br/>
$ docker images <br/>
  REPOSITORY   TAG       IMAGE ID       CREATED         SIZE <br/>
  demo         latest    9d4a0ec3294e   6 minutes ago   288MB <br/>
$ docker run -p 8443:8443 -e "SPRING_PROFILES_ACTIVE=dev" demo:latest <br/>
</pre>

## API OPERATIONS
### Save a new customer to database

Method : HTTP.POST <br/>
URL : https://localhost:8443/customer-info/customer/save <br/>
Request Body : <br/>
<pre>
{ 
    "name": "name1",
    "age": 1,
    "shippingAddress": {
        "address": {
            "streetName": "software",
            "city": "ankara",
            "country": "TR"
       }
    }
}
</pre>

Curl Request : <br/>
<pre>
curl --location --request POST 'https://localhost:8443/customer-info/customer/save' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5E6B21C9533643F4A7EE462DCBB3B312' \
--data-raw '{
    "name": "name1",
    "age": 1,
    "shippingAddress": {
        "address": {
            "streetName": "software",
            "city": "ankara",
            "country": "TR"
        }
    }
}'
</pre><br/>

Response : 

HTTP response code 200 <br/>
<pre>
{
    "id": 1,
    "name": "name1",
    "age": 1,
    "shippingAddress": {
        "id": 1,
        "address": {
            "id": 1,
            "streetName": "software",
            "city": "ankara",
            "country": "TR"
        }
    }
}
</pre>


### List all customers saved to database

Method : HTTP.GET <br/>
URL : https://localhost:8443/customer-info/customer/list <br/>
Request Body : <br/>
{}<br/>
Curl Request : <br/>
<pre>
curl --location --request GET 'https://localhost:8443/customer-info/customer/list' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5E6B21C9533643F4A7EE462DCBB3B312' \
--data-raw '{}'
</pre>
<br/>

Response : 

HTTP response code 200 <br/>
<pre>
[
    {
        "id": 1,
        "name": "name1",
        "age": 1,
        "shippingAddress": {
            "id": 1,
            "address": {
                "id": 1,
                "streetName": "software",
                "city": "ankara",
                "country": "TR"
            }
        }
    }
]
</pre>
<br/>

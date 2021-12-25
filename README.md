## spring-boot-enable-https-ssl-self-signed-certificate

Purpose : Enable https port 8443 for the spring boot application. <br/>
Reason : Provide secure data transfer between rest api and client.  <br/>

### Local run steps <br/>
1- Generate self signed ssl certificate by using JDK keytool. <br/>
D:\DEV\ssl>keytool -genkeypair -alias custapi -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore custapi.p12 -validity 3650 -storepass password <br/>
What is your first and last name? <br/>
[Unknown]: <br/>
What is the name of your organizational unit? <br/>
[Unknown]: <br/>
What is the name of your organization? <br/>
[Unknown]: <br/>
What is the name of your City or Locality? <br/>
[Unknown]: <br/>
What is the name of your State or Province? <br/>
[Unknown]: <br/>
What is the two-letter country code for this unit? <br/>
[Unknown]: <br/>
Is CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown correct? <br/>
[no]:  yes <br/>

2- Move generated certificate file into your spring boot application resources directory named with "ssl". <br/>
3- Start Spring Boot API by running main method containing class CustomerInfoApplication.java in your IDE. <br/>
4- Alternatively you can start your Docker container by following the commands below. <br/>
NOT : Execute maven command from where the pom.xml is located in the project directory to create Spring Boot executable jar. <br/>
<pre> 
$ mvn clean install -U -X <br/>
</pre>


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
NOT : Tested on Windows 10 with Docker Desktop Engine Version : 20.10.8 <br/>
<pre>
$ docker system prune -a --volumes <br/>
$ docker build . --tag demo  <br/>
$ docker images <br/>
  REPOSITORY   TAG       IMAGE ID       CREATED         SIZE <br/>
  demo         latest    9d4a0ec3294e   6 minutes ago   288MB <br/>
$ docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" demo:latest <br/>
</pre>

## API OPERATIONS
### Save a new customer to database

Method : HTTP.POST <br/>
URL : https://localhost:8443/customer-info/customer/save <br/>
Request Body : <br/>
{ <br/>
&ensp;    "name": "name1", <br/>
&ensp;    "age": 1, <br/>
&ensp;    "shippingAddress": { <br/>
&emsp;        "address": { <br/>
&emsp;            "streetName": "software", <br/>
&emsp;            "city": "ankara", <br/>
&emsp;            "country": "TR" <br/>
&emsp;        } <br/>
&ensp;    } <br/>
} <br/>

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

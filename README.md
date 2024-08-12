# Quiz-App-MicroService

1. The application contains question and quiz projects connected to seperate databases on PostGreSql and registered as eureka clients.
2. The Service-registry is the eureka server.
3. The api-gateway is also a eureka client routed to access quiz and question service through a common url.
4. The methods in quiz and question service can be accessed through url-:
   http://localhost:8765/quiz-service (or question-service)/......

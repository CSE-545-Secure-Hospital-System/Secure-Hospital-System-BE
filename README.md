# Secure-Hospital-System-BE
This is the backend project for the secure hospital system.

#### Run below in MySQL after starting the application

```
use cse545;
INSERT INTO roles(role_id, role) VALUES(1, 'PATIENT');
INSERT INTO roles(role_id, role) VALUES(2, 'ADMIN');
INSERT INTO roles(role_id, role) VALUES(3, 'HOSPITAL_STAFF');
```

APIs:
NoAuth APIs (No Authentication is required) :
- /api/auth/register 
- /api/auth/confirm
- /api/auth/login

Auth APIs (Authentication is required):
- Admin APIs
  - /api/admin/getAllUsers

Useful Links:
- https://swagger.io/specification/
- https://springdoc.org/#Introduction
- https://editor.swagger.io/
- https://www.baeldung.com/java-openapi-generator-server
- https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.0.2.md
- https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-logging.html
- https://spring.io/guides/gs/accessing-data-mysql/
- https://www.codejava.net/frameworks/spring-boot/spring-boot-security-role-based-authorization-tutorial

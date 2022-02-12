# Secure-Hospital-System-BE
This is the backend project for the secure hospital system.

#### Run below in MySQL after starting the application

```
use cse545;
INSERT INTO roles(role_id, role) VALUES(2, 'PATIENT');
INSERT INTO roles(role_id, role) VALUES(1, 'ADMIN');
-- Always keep admin id as 1
INSERT INTO roles(role_id, role) VALUES(3, 'HOSPITAL_STAFF');
```

Need to setUp admin account manually and all other remaining account types are created from UI based on requirements.

Admin account creation Process:

```
INSERT INTO `cse545`.`users`
(`user_id`,
`email`,
`enabled`,
`first_name`,
`last_name`,
`locked`,
`password`,
`phone`,
`session_id`)
VALUES
(1,
"spapani@asu.edu",
1,
"Saicharan",
"Papani",
0,
"Password",
"+14808031954",
null);
-- use Bcrypt password above

select * from cse545.users;
```

- https://bcrypt-generator.com/


Add the role as ADMIN for the above user:

```
INSERT INTO `cse545`.`user_roles`
(`user_id`,
`role_id`)
VALUES
(1,
1);
SELECT * FROM cse545.user_roles;
```

Temporarily admin is created manually. Later on it will be an automated process.


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

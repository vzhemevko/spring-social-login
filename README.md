## Spring Social Login

  * [What is Spring Social Login](#what-is-spring-social-login)
  * [Demo](#demo)
  * [Getting Started](#getting-started)
    + [Prerequisites](#prerequisites)
    + [Creating OAuth2 identity provider app ](#creating-oauth2-identity-provider-app)
    + [Start the application](#start-the-application)
  * [Contributing](#contributing)
  * [Authors](#authors)
  * [License](#license)
  * [Built With](#built-with)

     
------------

### What is Spring Social Login

Spring Social Login is a demo application of how to build authentication and authorization to your application based on 
OAuth2 identity providers, such as Facebook, GitHub, Google, and others.

------------

### Demo

![alt text](https://github.com/vzhemevko/spring-social-login/blob/master/demo/github-login.gif?raw=true)

------------

### Getting Started

#### Prerequisites

```
- installed - Git
- installed  - Java 11 
- OAuth2 Client or App ID and Secret from the identity provideryou are going to use for login. For example GitHub.
```

#### Creating OAuth2 identity provider app 

Let's take GitHub as a demo identity provider

Go to https://github.com/settings/apps to create a new app and get Client ID and Client secret after creation 

#### Start the application

Clone the repository

`git clone https://github.com/vzhemevko/spring-social-login.git`

After cloning the repository navigate to the root directory and build the project 

```
cd spring-social-login
gradlew build
```

Go to `src/main/resources/application.yml` and update you app GitHub ID and secret
 
``` 
github:
    client-id: <GITHUB APP ID>
    client-secret: <GITHUB APP SECRET>
```

Then you could run Spring Boot form the command line 

`gradlew bootRun`

Or create run/debug configuration in your IDE with the main class

```java 
org.vz.spring.social.login.SpringSocialLoginApplication
```

After go to http://localhost:8080

### Contributing

Feel free to submit pull requests

------------

### Authors

* **Vasyl Zhemevko** - *Initial work* - [vzhemevko](https://github.com/vzhemevko)

------------

### License

This project is licensed under the MIT License

------------

### Built With

* [Spring Boot](https://spring.io/projects/spring-boot) 
* [Thymeleaf](https://github.com/thymeleaf) 
* [Bootstrap](https://getbootstrap.com/) 

------------

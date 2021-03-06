# Proverbs
Sample java web application with proverb crawlers.
**Supported Websites For Crawling:**
- [atasozleri.gen.tr](http://www.atasozleri.gen.tr/)
## 1. System Requirement
- Java 8 or later.
- Tomcat 8 or later.
## 2. Architecture
![proverbs architecutre 2](https://user-images.githubusercontent.com/6253588/34536774-44b6972e-f0ec-11e7-886c-b7530b82abeb.png)
## 3. Technology Stack
- Spring Framework: For core architecture and design principles and patterns like MVC, IOC, events, etc.
- Hibernate Validation (JSR-303): For user input validation.
- Spring Data: For data access abstraction.
- JSTL: For JSP abstraction.
- Hibernate (JPA Implemenation): For implementing ORM design pattern for data access.
- jsoup: For HTML parser.
- Logback: For logging/auditing.
- Jackson: For serialization/deserialization POJO to/from JSON.

## 4. Usage
- Build war using maven (mvn package).
- Copy war file to tomcat webapp directory.
- Run server
- Visit localhost:<port>/proverbs.
  
## 5. Remaining Tasks
- [ ] Develop & implement caching strategy.

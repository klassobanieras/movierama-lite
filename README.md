# MovieRama Lite

**MovieRama Lite** is a lightweight movie application where users can like or dislike movies, and the system tracks the number of likes and dislikes for each movie. Each user carries their own information about the movies they have liked or disliked. The application is designed for quick rendering and efficient updates of movie reaction counts, with future scalability in mind.

## Highlights

### 1. **User Reaction Management**
- Users carry their own data regarding the movies they have liked or disliked, stored individually in the system.
- Movies themselves only store the **count** of likes and dislikes rather than individual user reactions, ensuring minimal computation when rendering the movie list.

### 2. **Efficient Count Updates**
- The like and dislike counts for movies are updated asynchronously using **application events**.
- When a user likes or dislikes a movie, the corresponding event is published and handled asynchronously, ensuring a non-blocking user experience.
- The current setup updates the counts in real-time, but future improvements could involve batching updates for increased performance at scale.

### 3. **Optimized for Quick Renders**
- The application is designed to **eliminate computational overhead** when retrieving the number of likes and dislikes for each movie. Since the counts are maintained independently of the users’ reaction data, this allows for **fast rendering** of movie lists.
- By pre-calculating and storing counts, race conditions are minimized, and updates to movie reactions are handled without risking conflicts when modifying the same database rows.

### 4. **Scalable and Race Condition Resistant**
- The architecture is designed to **eliminate race conditions** by preventing simultaneous updates to the same database rows. Since user reactions and movie counts are stored separately, updates can be made safely without locking issues.
- In the future, the async event handling mechanism could be extended to update reaction counts in **batches**, further improving the system’s performance under high loads.

## Running the Application

The application is containerized using **Docker** and can be easily run using **Docker Compose**. Follow the steps below to get the application up and running.

### Prerequisites
- Docker installed on your system.
- Docker Compose installed.

### Steps to Run the Application

1. **Clone the Repository**

2. **Run Docker Compose**
   The `docker-compose.yml` file will spin up two services:
    - The **Spring Boot application** container.
    - A **PostgreSQL** database container.

   To start the application, simply run:

   ```bash
   docker-compose up
   ```

   This command will:
    - Start the **PostgreSQL** database on port `5432`.
    - Start the **Spring Boot** application on port `8080`.

3. **Access the Application**

   Once the containers are up and running, you can access the application by navigating to:

   ```
   http://localhost:8080
   ```

4. **Stopping the Application**

   To stop the application and remove the containers, run:

   ```bash
   docker-compose down
   ```
   
5. **Troubleshooting**
  
  If the app fails to run on an amd64 machine change the image to jtsompos/lite:latest

### Docker Compose Configuration Overview

The `docker-compose.yml` defines two services:

- **App (Spring Boot)**: The Spring Boot app runs in a Docker container using your custom image. The app is configured to connect to the PostgreSQL database.
- **Postgres (Database)**: A PostgreSQL instance is set up to persist the movie and user data.

```yaml
services:
   app:
      image: jtsompos/lite:native
      container_name: movierama-lite-app
      ports:
         - "8080:8080"
      environment:
         - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/movierama
         - SPRING_DATASOURCE_USERNAME=myuser
         - SPRING_DATASOURCE_PASSWORD=mypassword
      depends_on:
         - db
      networks:
         - lite
   db:
      image: 'postgres:latest'
      container_name: db
      environment:
         - 'POSTGRES_DB=movierama'
         - 'POSTGRES_PASSWORD=mypassword'
         - 'POSTGRES_USER=myuser'
      ports:
         - '5432:5432'
      networks:
         - lite
networks:
   lite:
      driver: bridge
```

## Future Improvements

- **Caching**: Likes, dislikes or event whole movies could be cached for better performance
- **Pagination**: Movies could be paginated
- **Authentication/Authorization**: Authentication could be moved to a standalone system and tokens could be shared to the resource applications
- **Batch Processing**: The current asynchronous event handling system for updating likes and dislikes could be extended to support **batch updates** in the future. This would improve performance and reduce the load on the database when handling large volumes of reactions.

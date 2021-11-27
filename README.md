# Discord Music Bot


The project is about discord music bot that you can add and play the music with comfortable feature. we developed the bot by java 

### Prerequisite

- Java 11
- Maven


### Bot Command
`!help` for explain the other command usage: `!help <command>`

`!ping` for check ping of discord server , usage: `!ping`

`!join` for add the bot to join voice channel , usage: `!join`

`!play` for play the music or playlist , usage: `!play <youtube link or existing music or playlist name in system>`

`!stop` for stop music and clear from queue , usage:`!stop`

`!skip` for make the track to skip the current track , usage: `!skip`

`!add` for add music to the system , usage: `!add <music or playlist name> <youtube link>`

`!get` for get all list music and playlist name , usage: `!get`


### Deploy Step

1. Clone git repo to AWS EC2
2. Use `mvn clean install` to build the jar file
3. Create `Dockerfile` to build the docker image 

```
FROM openjdk:11.0.13-jdk
ARG JAR_FILE=target/xxx.jar
COPY ${JAR_FILE} app.jar
ENV TOKEN=xxx
ENV PREFIX=xxx
ENV OWNER_ID=xxx
ENV MONGO_CLIENT_URL=xxx
ENV DB_NAME=xxx
ENV MUSIC_COLLECTION=xxx
ENV PLAYLIST_COLLECTION=xxx
ENTRYPOINT ["java","-jar","/app.jar"]
```

4. Build the Dockerfile usage: `sudo docker build -t discord-music-bot-img .`
5. Run the container usage: `sudo docker run -d --name discord-music-bot-container discord-music-bot-img` 


#### Library

[Discord bot](https://github.com/Discord4J/Discord4J) ( Library for implemented bot )

[Java Discord API](https://github.com/DV8FromTheWorld/JDA) ( Library for implemented bot )

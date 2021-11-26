package discord.music.bot.repo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import discord.music.bot.Config;
import discord.music.bot.db.MongoConnection;
import discord.music.bot.model.Music;
import org.bson.conversions.Bson;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MusicRepo {
    private static MusicRepo musicRepo;
    private MongoCollection<Music>  musicCollection;
    private String collectionName;

    private MusicRepo() {
        this.collectionName = Config.get("music_collection");
        this.musicCollection =  MongoConnection.getMongoClient().db.getCollection(this.collectionName,Music.class);
    }

    public static MusicRepo getMusicRepo() {
        if(musicRepo == null){
            musicRepo = new MusicRepo();
        }
        return musicRepo;
    }

    public void insertMusic(String musicName,String musicLink){
        Music music = new Music();
        music.setMusicId(UUID.randomUUID().toString());
        music.setMusicName(musicName);
        music.setMusicLink(musicLink);

        this.musicCollection.insertOne(music);
    }

    public List<Music> getMusics(){
        List<Music> musics = new ArrayList<>();
        MongoCursor<Music> cursor =this.musicCollection.find().cursor();
        while (cursor.hasNext()){
            musics.add(cursor.next());
        }

        return musics;
    }

    public Music getByMusicName(String musicName){
        Bson filter = Filters.eq("musicName",musicName);

        return this.musicCollection.find(filter).first();
    }
}

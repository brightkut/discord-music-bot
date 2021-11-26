package discord.music.bot.repo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import discord.music.bot.Config;
import discord.music.bot.db.MongoConnection;
import discord.music.bot.model.Music;
import discord.music.bot.model.Playlist;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PlaylistRepo {
    private static PlaylistRepo playlistRepo;
    private MongoCollection<Playlist> playlistCollection;
    private String collectionName;

    private PlaylistRepo() {
        this.collectionName = Config.get("playlist_collection");
        this.playlistCollection = MongoConnection.getMongoClient().db.getCollection(this.collectionName,Playlist.class);
    }

    public static PlaylistRepo getPlaylistRepo() {
        if(playlistRepo == null){
            playlistRepo = new PlaylistRepo();
        }
        return playlistRepo;
    }

    public void insertPlaylist(String playlistName, String playlistLink){
        Playlist playlist = new Playlist();
        playlist.setPlaylistId(UUID.randomUUID().toString());
        playlist.setPlaylistName(playlistName);
        playlist.setPlaylistLink(playlistLink);

        this.playlistCollection.insertOne(playlist);
    }

    public List<Playlist> getPlaylists(){
        List<Playlist> playlists = new ArrayList<>();
        MongoCursor<Playlist> cursor =this.playlistCollection.find().cursor();
        while (cursor.hasNext()){
            playlists.add(cursor.next());
        }

        return playlists;
    }

    public Playlist getByPlayListName(String playlistName){
        Bson filter = Filters.eq("playlistName",playlistName);

        return this.playlistCollection.find(filter).first();
    }
}

package discord.music.bot.db;


import com.mongodb.ConnectionString;
import com.mongodb.DB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import discord.music.bot.Config;
import discord.music.bot.model.Music;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnection {
    private MongoClient mongoClient;
    private static MongoConnection mongoConnection;
    private  String dbName;
    private String mongoClientUrl;
    public MongoDatabase db;

    private MongoConnection () {
        this.mongoClientUrl = Config.get("mongo_client_url");
        this.dbName = Config.get("db_name");

        ConnectionString connectionString = new ConnectionString(this.mongoClientUrl);

        // -- setting for mapping object in mongo
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());

        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);


        MongoClientSettings settings = MongoClientSettings
                .builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.db = this.mongoClient.getDatabase(this.dbName);
    }

    public static MongoConnection getMongoClient() {
        if(mongoConnection == null){
            mongoConnection = new MongoConnection();
        }
        return mongoConnection;
    }
}

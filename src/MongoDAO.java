import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDAO {
	static MongoClient mc = new MongoClient(new ServerAddress("127.0.0.1",27017));
	static DB db = mc.getDB("genie");
	static DBCollection collection = db.getCollection("genie_music");
	
	public static void genieInsert(GenieVO vo) {
		BasicDBObject obj = new BasicDBObject();
		obj.put("songid", vo.getSongid());
		obj.put("rank", vo.getRank());
		obj.put("imgsrc", vo.getImgsrc());
		obj.put("title", vo.getTitle());
		obj.put("artist", vo.getArtist());
		obj.put("album", vo.getAlbum());
		collection.insert(obj);
	}
}

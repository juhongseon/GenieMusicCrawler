import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainClass {

	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(2020,0,1);
		
		Calendar today = Calendar.getInstance();
		while(cal.before(today)) {
			int month = cal.get(Calendar.MONTH)+1;
			String mm;
			if(month<10) mm = "0"+month;
			else mm = String.valueOf(month);
			
			int date = cal.get(Calendar.DATE);
			String dd;
			if(date<10) dd = "0"+date;
			else dd = String.valueOf(date);
			
			String ymd = "2020"+mm+dd;
			System.out.println(ymd);
			getMusicData(ymd);
			cal.add(Calendar.DATE, 1);
		}

	}
	
	public static void getMusicData(String ymd) {
		for(int pg=1; pg<=4; pg++) {
			try {
				Document doc = Jsoup.connect("https://www.genie.co.kr/chart/top200?ditc=D&ymd="+ymd+"&hh=12&rtm=Y&pg="+pg).get();
				Elements rows = doc.select("table.list-wrap tbody tr");
				for(Element row : rows) {
					GenieVO vo = new GenieVO();
					vo.setYmd(Integer.parseInt(ymd));
					
					String songid = row.attr("songid");
					System.out.println(songid);
					vo.setSongid(Integer.parseInt(songid));
					
					String rank = row.selectFirst("td.number").text();
					rank = rank.split(" ")[0];
					System.out.println(rank);
					vo.setRank(Integer.parseInt(rank));
					
					String imgsrc = row.selectFirst("a.cover img").attr("src");
					System.out.println(imgsrc);
					vo.setImgsrc(imgsrc);
					
					String title = row.selectFirst("td.info a.title").text();
					System.out.println(title);
					vo.setTitle(title);
					
					String artist = row.selectFirst("td.info a.artist").text();
					System.out.println(artist);
					vo.setArtist(artist);
					
					String album = row.selectFirst("td.info a.albumtitle").text();
					System.out.println(album);
					vo.setAlbum(album);
					
					System.out.println("================================");
					MongoDAO.genieInsert(vo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
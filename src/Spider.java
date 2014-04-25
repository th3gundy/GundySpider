import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class Spider {
 
	private final String USER_AGENT = "Mozilla/5.0";
	public static void main(String[] args) throws Exception {
 
		Spider http = new Spider();
		String choice = "";
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Site ( Ex: example.com ) : ");
		String site = scanner.nextLine();
		System.out.print("File Type ( Ex: pdf, txt .. ) : ");
		String fileType = scanner.nextLine();
		
		http.sendGet(site,fileType);
		System.out.println("\n<<< ------------------- >>>");
		System.out.print("Quit (q) or Download (d) : ");
		choice = scanner.nextLine();
		
        while( !(choice.equalsIgnoreCase("q")) ){
		  	System.out.print("\nURL to be downloaded : ");
			String downLink = scanner.nextLine();
			System.out.print("File Path : ");
			String filePath = scanner.nextLine();
			new Downloader().downloadFile(downLink, filePath);
			System.out.print("Quit (q) or Download (d) : ");
			choice = scanner.nextLine();
        }
      	System.out.println("\nQuit");
	}
	private void sendGet(String site,String fileType) throws Exception {
 
		String url = "https://www.google.com/search?num=500&q=site:"+site+"+filetype:"+fileType;
     try {
    	
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Connecting to Google \n");
		
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);
	if(responseCode == HttpURLConnection.HTTP_OK){
		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
		String inputLine = "";
		System.out.println("\nAnalysing URL...");
		String regex = "(><a href=\"/url\\?q=)"+"(\\S+)"+"(&amp;sa=U&amp;)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputLine);
		
		int counter=0;
		while ((inputLine = in.readLine()) != null) {
			m = p.matcher(inputLine);
			if (m.find()) {
				System.out.println("\n<<< ------------------- >>>\n");
				System.out.println("URL : " + m.group(2));
				counter++;
			}
		}
		
		in.close();
		System.out.println("\nTotal number of URL : " + counter );
	} else {
		System.err.println("Bad Connection. Responce Code : " + responseCode );
		System.exit(0);
	}
     } catch (Exception e) {
		System.err.println("Err�r : " + e.getMessage() );
	  }
   }
 
	
}

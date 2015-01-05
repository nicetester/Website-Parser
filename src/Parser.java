import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {
		static String href="href=\"";
		char[]ending = new char[7];	
		static String link="";
		static ArrayList<String> linksArray = new ArrayList<String>();
		static char quoteType;
		static int numberOfLinks;
		static Scanner scan;
		static String stringUrl;
		
	public static void main(String[]args) throws IOException{
	
		stringUrl="http://cnn.com";
		connectToUrl(stringUrl);
		findLinks();
		loop();
		
		
	}
	public static void connectToUrl(String stringUrl) throws IOException{
		
		URL url = new URL(stringUrl);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		 connection.setRequestProperty("Content-Language", "en-US"); 
	        connection.setRequestProperty("User-Agent",
	                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
		connection.connect();

		//BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		scan = new Scanner(connection.getInputStream());
		
	}
	public static void findLinks() throws IOException{
		while(scan.hasNextLine()){
			boolean done=false;
			String line = scan.nextLine();
			String currentLink="";
			if(line.contains(href)){
				for(int x=0;x<line.length();x++){
					try{
						if((line.charAt(x)=='h')&&(line.charAt(x+1)=='r')&&(line.charAt(x+2)=='e')&&(line.charAt(x+3)=='f')&&(line.charAt(x+4)=='=')&&(line.charAt(x+5)=='"')){
							numberOfLinks++;
							System.out.println(numberOfLinks+" many links have been seen");
							char start=line.charAt(x+6);
							for(int y=0;y<1000;y++){
								if(!(line.length()<=x+y+6)){
									if(line.charAt(x+6+y)!='"'){ //searches for either " or '
										currentLink+=line.charAt(x+6+y);
									}
									else{
										if(currentLink.charAt(0)=='h'){
											linksArray.add(currentLink);
											System.out.println(currentLink);
											getTitles(currentLink);
										}
										break;
									}
								}
								else{
									System.out.println("FAILURE!!");
									break;
								}
	
							}
							//currentLink = currentLink.substring(0, currentLink.length()-1); //removes last char in string (")
							//System.out.println(currentLink);
						
						}
					}catch(StringIndexOutOfBoundsException e){
						continue;
					}
					
				}
				
				

			}
		}
		System.out.println();
	}
	public static void loop(){
			for(int x=0;x<linksArray.size();x++){
				try{
					connectToUrl(linksArray.get(x));
					findLinks();
				}
				catch(FileNotFoundException e){
					continue;
				}
				catch(IOException r){
					continue;
				}
			}
		
		
		
	}
	public static void getTitles(String url){
		
		
	}
	
}
		

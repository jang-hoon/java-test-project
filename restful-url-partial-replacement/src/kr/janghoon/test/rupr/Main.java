package kr.janghoon.test.rupr;

public class Main {

	public static void main(String[] args) {
		String[] testUrls = new String[]{
			"/was-test/podcasts/1263241",	
			"/was-test/podcasts/1263241/podcasts",
			"/was-test/1263241/podcasts?id=10",
			"/was-test/1263241/1263241",
			"/1263241/1263241/1263241",
			"/was-test/podcasts",
			"/was-test/podcasts/1263241?id=10&name=abc&age=20",
			"/1263241/podcasts",
			"/123was-test/123456",
			"/was-test123/123456",
			"adsasadasd",
			"adsas/adasd"
		};
		
		StringBuilder builder = new StringBuilder();
		for(String testUrl : testUrls){
			builder.setLength(0);
			String[] urls = testUrl.split("[?]");
			String[] paths = urls[0].split("[/]");
			for(int i=1; i<paths.length; i++){
				String path = paths[i];
				try{
					Integer.parseInt(path);
					path = "###";
				}catch (NumberFormatException e) {

				}
				builder.append("/");
				builder.append(path);
			}

			if(urls.length > 1){
				builder.append("?" + urls[1]);
			}
			
			String replacedUrl = paths[0] + builder.toString();
//			String replacedUrl = testUrl.replaceAll("/[0-9]+", "/###");
			System.out.println(String.format("%s -> %s", testUrl, replacedUrl));
		}
	}
}

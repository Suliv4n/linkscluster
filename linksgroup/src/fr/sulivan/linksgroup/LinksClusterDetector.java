package fr.sulivan.linksgroup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinksClusterDetector {
	
	private URL url;
	private Document document;
	
	private HashSet<Element> tested;

	public ArrayList<LinksCluster> linksClusters;
	
	public LinksClusterDetector(String url, int timeout) throws IOException{
		
		tested = new HashSet<Element>();
		linksClusters = new ArrayList<LinksCluster>();
		
		this.url = new URL(url);
		document = Jsoup.parse(this.url, timeout);

		Elements elements = document.select("body *:not(a)");
		

		for(Element element : elements){
			if(!tested.contains(element)){
				process(element);
			}
		}
		
		detectClustercepetion();
	}
	
	
	public LinksClusterDetector(Document document) throws IOException{
		
		tested = new HashSet<Element>();
		linksClusters = new ArrayList<LinksCluster>();
		
		this.document = document;

		Elements elements = document.select("body *:not(a)");
		

		for(Element element : elements){
			if(!tested.contains(element)){
				process(element);
			}
		}
		
		detectClustercepetion();
	}
	
	
	
	private boolean process(Element element){

		
		Elements links = element.select("a");
		
		
		//pas de liens
		if(links.size() <= 1){
			return false;
		}
		
		String linksText = "";
		
		
		for(int i=0; i<links.size(); i++){
			linksText += links.get(i).text();
		}

		
		//Plus d'autres choses que de liens
		if(!(element.text().length() == 0) && (float)linksText.replaceAll("\\n|\\s|\\t|\\r", "").length() / (float)element.text().replaceAll("\\n|\\s|\\t|\\r", "").length() < 0.75f){
			return false;
		}
		
		
		Element deepestUniqChild = element;
		while(deepestUniqChild.children().size() == 1){
			if(tested.contains(deepestUniqChild)){
				return false;
			}
			deepestUniqChild = deepestUniqChild.children().get(0);
		}
		
		Element lastUniqParent = element;
		while(lastUniqParent.parent().children().size() == 1 && !lastUniqParent.tagName().equalsIgnoreCase("body")){
			if(tested.contains(lastUniqParent)){
				return false;
			}
			lastUniqParent = lastUniqParent.parent();
		}
		
		if(!tested.contains(lastUniqParent)){
			linksClusters.add(new LinksCluster(lastUniqParent));
			tested.add(lastUniqParent);
		}
		
		return true;

	}
	
	private void detectClustercepetion(){
		for(LinksCluster c1 : linksClusters){
			for(LinksCluster c2 : linksClusters){
				if(c1 != c2 && c1.getElement().select("*").contains(c2.getElement())){
					c2.addParent(c1);
				}
			}
		}
	}
	
	public ArrayList<LinksCluster> getLinksClusters(){
		return linksClusters;
	}
	
}

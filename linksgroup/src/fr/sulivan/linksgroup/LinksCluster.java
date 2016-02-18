package fr.sulivan.linksgroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.jsoup.nodes.Element;



public class LinksCluster {
	
	private Element element;
	private ArrayList<LinksCluster> parents;
	
	public LinksCluster(Element element){
		parents = new ArrayList<LinksCluster>();
		this.element = element;
	}
	
	@Override
	public String toString(){
		return element.cssSelector();
	}

	public Element getElement() {
		return element;
	}

	public void addParent(LinksCluster parent) {
		parents.add(parent);
	}
	
	public boolean hasParents(){
		return !parents.isEmpty();
	}
	
	public ArrayList<Element> getPhysicPath(){
		Element parent = element;
		ArrayList<Element> physicPath = new ArrayList<>();
		
		while(!parent.tagName().equalsIgnoreCase("body")){
			physicPath.add(parent);
			parent = parent.parent();
		}
		Collections.reverse(physicPath);
		return physicPath;
	}
	
	public int diffTree(LinksCluster other, boolean strict){

		int diff = Math.abs(this.element.getAllElements().size() - other.element.getAllElements().size());

		diff += diffElement(this.element, other.element, strict);
	
		return diff;
	}
	
	public int diffTree(LinksCluster other){
		return diffTree(other, false);
	}
	
	private int diffElement(Element element1, Element element2, boolean strict){
		int diff = 0;
		
		for(int i=0; i<Math.min(element1.children().size(), element2.children().size()); i++){
			if(!element1.children().get(i).equals(element2.children().get(i)) 
					|| (!strict && !element1.children().get(i).tagName().equalsIgnoreCase(element2.children().get(i).tagName()) )){
				diff++;
			}
			diff += diffElement(element1.children().get(i), element2.children().get(i), strict);
		}
		return diff;
	}
	
	public int diffLinks(LinksCluster other){
		int diff = 0;
		
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> others = new ArrayList<String>();
		
		for(Element link : this.element.select("a")){
			links.add(link.absUrl("href"));
		}
		for(Element link : other.element.select("a")){
			others.add(link.absUrl("href"));
		}
		
		for(String href : links){
			if(others.contains(href)){
				others.remove(href);
			}
			else{
				diff++;
			}
		}
		
		return diff;
	}
	
	public int diffContent(LinksCluster other){
		int diff = 0;
		String source = this.element.text().replaceAll("\\n|\\s|\\t|\\r", "");
		String target = other.element.text().replaceAll("\\n|\\s|\\t|\\r", "");

		for(int i=0; i<Math.min(source.length(), target.length());i++){
			if(source.charAt(i) != target.charAt(i) || (source.charAt(i) != target.charAt(i))){
				diff++;
			}
		}
		
		return diff + Math.abs(source.length() - target.length());
	}

	public String getPhysicPathInLine(){
		
		String res = "";
		for(Element element : getPhysicPath()){
			res += element.tagName() + " ";
		}
		
		return res.substring(0, res.length()-1);
	}
	
	public boolean hasId(String id){
		Element child = element;
		
		do{
			if(child.attr("id").equals(id)){
				return true;
			}
			child = child.child(0);
		}while(child.children().size() == 1);
		
		return false;
	}
	
	public boolean hasClass(String clazz){
		Element child = element;
	
		do{
			if(Arrays.asList(child.attr("class").split(" ")).contains(clazz)){
				return true;
			}
			child = child.child(0);
		}while(child.children().size() == 1);
		
		return false;
	}
}

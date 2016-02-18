package fr.sulivan.linksgroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Launcher {

	public static void main(String[] args) {
		try {
			ArrayList<LinksClusterDetector> pages = new ArrayList<LinksClusterDetector>();
			

			//LinksClusterDetector test = new LinksClusterDetector(Jsoup.parse(new File("html/verysimple.html"), "UTF-8"));
			LinksClusterDetector test2 = new LinksClusterDetector("http://forum.magicmaman.com/showthread.php?674066-Test/page4", 2000000);
			LinksClusterDetector test1 = new LinksClusterDetector("http://forum.magicmaman.com/showthread.php?674066-Test/page4", 2000000);
			
			
			/*System.out.println(test2.getLinksClusters().get(2).diffTree(test.getLinksClusters().get(1)));
			System.out.println(test2.getLinksClusters().get(0).getElement());
			System.out.println(test.getLinksClusters().get(0).getElement());*/
			/*
			pages.add(detector1);
			pages.add(detector2);
			pages.add(detector3);
			pages.add(detector4);
			pages.add(detector5);
			*/
			//Exemple calcul de nombres liens différents dans 2 clusters
			//System.out.println(detector1.getLinksClusters().get(0).diffLink(detector2.getLinksClusters().get(2)));
			//System.out.println(detector1.getLinksClusters().get(0).getElement().select("a").size());
			
			//Exemple comparaison path
			//System.out.println(detector1.getLinksClusters().get(0).comparePath(detector2.getLinksClusters().get(2)));
			
			//detector.getLinksClusters().stream().filter(c -> !c.hasParents()).forEach(System.out::println);
			
			//detector1.getLinksClusters().stream().forEach(System.out::println);
			//System.out.println("Found : " + detector1.getLinksClusters().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

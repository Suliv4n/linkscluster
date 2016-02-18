package linksgroup;

import java.io.File;
import java.io.IOException;



import org.jsoup.Jsoup;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import fr.sulivan.linksgroup.LinksClusterDetector;

public class LinksClusterTest {

	private LinksClusterDetector lcd1;
	private LinksClusterDetector lcd2;
	
	@Before 
	public void init(){
		try {
			lcd1 = new LinksClusterDetector(Jsoup.parse(new File("html/verysimple.html"), "UTF-8"));
			lcd2 = new LinksClusterDetector(Jsoup.parse(new File("html/medium.html"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void detectCluster() {
		assertEquals(2, lcd1.getLinksClusters().size());
		assertEquals("container1", lcd1.getLinksClusters().get(0).getElement().attr("id"));
		assertEquals("container2", lcd1.getLinksClusters().get(1).getElement().attr("id"));
		
		assertEquals(3, lcd2 .getLinksClusters().size());
		assertEquals("container1", lcd2.getLinksClusters().get(0).getElement().attr("id"));
		assertEquals("subcontainer1_1", lcd2.getLinksClusters().get(1).getElement().attr("id"));
		assertEquals("container2", lcd2.getLinksClusters().get(2).getElement().attr("id"));
	}
	
	@Test
	public void  diffTree(){
		assertEquals(2, lcd2.getLinksClusters().get(2).diffTree(lcd1.getLinksClusters().get(1)));
		//4 manquants + 1 différents
		assertEquals(5, lcd2.getLinksClusters().get(0).diffTree(lcd1.getLinksClusters().get(0)));
	}
	
	@Test
	public void diffLinks(){
		assertEquals(0, lcd2.getLinksClusters().get(2).diffLinks(lcd1.getLinksClusters().get(1)));
		assertEquals(2, lcd2.getLinksClusters().get(1).diffLinks(lcd1.getLinksClusters().get(0)));
	}

	@Test
	public void physicalPath(){
		assertEquals("div p", lcd2.getLinksClusters().get(1).getPhysicPathInLine());
	}
	
	@Test
	public void diffContent(){
		assertEquals(1, lcd2.getLinksClusters().get(2).diffContent(lcd1.getLinksClusters().get(1)));
	}
	
	@Test
	public void hasIdClass(){
		assertTrue(lcd2.getLinksClusters().get(1).hasClass("test"));
		assertTrue(lcd2.getLinksClusters().get(0).hasId("test"));
	}

}

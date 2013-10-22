/**
 * 
 */
package edu.wm.cs301.UI;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class provides functionality to write a maze to a file in an XML format.
 * The class design is not object-oriented as methods for this particular function are all collected here and
 * not distributed across classes that carry that information, e.g. BSPNode.
 * All methods are static. 
 * The XML format is a straightforward enumeration of elements and not particularly sophisticated. 
 * 
 *
 */
public class MazeFileWriter {

	/**
	 * Write maze content to a file
	 */
	public static void store(String filename, int width, int height, int rooms, int expected_partiters, BSPNode root, Cells cells, int[][] dists, int startX, int startY)
	{
		 try {
			 
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();

				 
				// root elements
				Element mazeXML = doc.createElement("Maze");
				doc.appendChild(mazeXML);
				
				// store fields of Maze class
				appendChild(doc, mazeXML, "sizeX", width) ;
				appendChild(doc, mazeXML, "sizeY", height) ;
				appendChild(doc, mazeXML, "roomNum", rooms) ;                 // TODO: check, unclear if this is truly necessary
				appendChild(doc, mazeXML, "partiters", expected_partiters) ;  // TODO: check, unclear if this is truly necessary
				// cells
				int number = 0 ;		
				for ( int x = 0; x != width; x++) {
					for ( int y = 0; y != height; y++) {
						appendChild(doc, mazeXML, "cell"+ "_" + Integer.toString(number), cells.getCells(x, y)) ;
						number++;
					}
				}
				// distances
				number = 0 ;		
				for ( int x = 0; x != width; x++) {
					for ( int y = 0; y != height; y++) {
						appendChild(doc, mazeXML, "dists"+ "_" + Integer.toString(number), dists[x][y]) ;
						number++;
					}
				}
				// start position
				appendChild(doc, mazeXML, "startX", startX) ;
				appendChild(doc, mazeXML, "startY", startY) ;
				// BSPnodes
				if (null != root)
				{
					storeBSPNode(root, doc, mazeXML,0);
				}
				else
				{
					System.out.println("MazeBuilderWriter.store: root node of BSP tree is null");
				}
				
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(filename));
		 
				transformer.transform(source, result);
			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
	}
	
	/**
	 * Store the content of a BSPNode including data of branches and leaves as special cases.
	 * @param root is the node considered
	 * @param doc document to add data to
	 * @param mazeXML element to add data to
	 * @param number is an index number for this node in the XML format
	 * @return
	 */
	private static int storeBSPNode(BSPNode root, Document doc, Element mazeXML, int number) {
		// xlBSPNode elements
		appendChild(doc, mazeXML, "xlBSPNode_" + number, root.xl) ;
		// ylBSPNode elements
		appendChild(doc, mazeXML, "ylBSPNode_" + number, root.yl) ;
		// xuBSPNode elements
		appendChild(doc, mazeXML, "xuBSPNode_" + number, root.xu) ;
		// yuBSPNode elements
		appendChild(doc, mazeXML, "yuBSPNode_" + number, root.yu) ;
		// isleafBSPNode elements
		appendChild(doc, mazeXML, "isleafBSPNode_" + number, root.isleaf) ;
		
		// store date for branches
		if (root.getClass() == BSPBranch.class)
		{
			number = storeBSPBranch((BSPBranch)root, doc, mazeXML, number);
		}
		else
		{
			// store data for leaf nodes
			if (root.getClass() == BSPLeaf.class)
				storeBSPLeaf((BSPLeaf)root, doc, mazeXML, number);
			else
				System.out.println("MazeFileWriter.storeBSPNode: Warning: skipping unknown BSBNode subclass") ;
		}
		return number ; // returns that largest number this method has used
	}
	
	/**
	 * Store the content of a leaf node, in particular its list of segments.
	 * All entries carry the number of the node as an index and each segment has an additional second index for the segment number.
	 * @param n is the node considered
	 * @param doc document to add data to
	 * @param mazeXML element to add data to
	 * @param number is an index number for this node in the XML format
	 */
	private static void storeBSPLeaf(BSPLeaf n, Document doc, Element mazeXML, int number) {
		if (n.isleaf == false)
			System.out.println("WARNING: isleaf flag and class are inconsistent!");
		// store list of segments, store total number of elements first
		appendChild(doc, mazeXML, "numSeg_" + number, n.slist.size()) ;
		int i = 0 ;
		for (Seg s : n.slist)
		{
			appendChild(doc, mazeXML, "distSeg_" + number+ "_" + i, s.dist) ;
			appendChild(doc, mazeXML, "dxSeg_" + number+ "_" + i, s.dx) ;
			appendChild(doc, mazeXML, "dySeg_" + number+ "_" + i, s.dy) ;
			appendChild(doc, mazeXML, "partitionSeg_" + number+ "_" + i, s.partition) ;
			appendChild(doc, mazeXML, "seenSeg_" + number+ "_" + i, s.seen) ;
			appendChild(doc, mazeXML, "xSeg_" + number+ "_" + i, s.x) ;
			appendChild(doc, mazeXML, "ySeg_" + number+ "_" + i, s.y) ;
			appendChild(doc, mazeXML, "colSeg_" + number+ "_" + i, (Integer) null) ;//*CHECK THIS* 
			i++ ;
		}
	}
	/**
	 * Store the content of a branch node, in particular its left and right children
	 * 
	 * The method recursively stores BSP nodes for left and right children.
	 * Note that the numbering schemes needs to match with the MazeFileReader class.
	 * 
	 * @param n is the node considered
	 * @param doc document to add data to
	 * @param mazeXML element to add data to
	 * @param number is an index number for this node in the XML format
	 * @return the highest used index number
	 */
	private static int storeBSPBranch(BSPBranch n, Document doc, Element mazeXML, int number) {
		if (n.isleaf == true)
			System.out.println("WARNING: isleaf flag and class are inconsistent!");
		// store: x, y, dx, dy
		appendChild(doc, mazeXML, "xBSPNode_" + number, n.x) ;
		appendChild(doc, mazeXML, "yBSPNode_" + number, n.y) ;
		appendChild(doc, mazeXML, "dxBSPNode_" + number, n.dx) ;
		appendChild(doc, mazeXML, "dyBSPNode_" + number, n.dy) ;
		// recursively store left and right branches
		if (n.lbranch == null)
		{
			// this is likely to be dead code as BSPBranches seem to have always 2 children
			number++ ;
			appendChild(doc, mazeXML, "xlBSPNode_" + number, Integer.MIN_VALUE) ;
		}
		else
		{
			// recursion
			number++ ;
			number = storeBSPNode(n.lbranch, doc, mazeXML, number) ;
		}
		// it is important that the recursion on the left branch updates the number value
		// such that for the nodes on the right branch we use new unique numbers
		if (n.rbranch == null)
		{
			// this is likely to be dead code as BSPBranches seem to have always 2 children
			number++ ;
			appendChild(doc, mazeXML, "xlBSPNode_" + number, Integer.MAX_VALUE) ;
		}
		else
		{
			// recursion
			number++ ;
			number = storeBSPNode(n.rbranch, doc, mazeXML, number) ;
		}
		return number ; // return the last number that was used
	}
	/**
	 * Append an new element to mazeXML that carries the given name has a child node with the given value.
	 * @param doc document to add data to
	 * @param mazeXML element to add data to
	 * @param name specifies the XML element to write to
	 * @param value is the content for the XML element
	 */
	private static void appendChild(Document doc, Element mazeXML, String name, int value)
	{
		Element e = doc.createElement(name);
		e.appendChild(doc.createTextNode(Integer.toString(value)) );
		mazeXML.appendChild(e);
	}
	/**
	 * Append an new element to mazeXML that carries the given name has a child node with the given value.
	 * @param doc document to add data to
	 * @param mazeXML element to add data to
	 * @param name specifies the XML element to write to
	 * @param value is the content for the XML element
	 */
	private static void appendChild(Document doc, Element mazeXML, String name, boolean value)
	{
		Element e = doc.createElement(name);
		e.appendChild(doc.createTextNode(Boolean.toString(value)) );
		mazeXML.appendChild(e);
	}
}

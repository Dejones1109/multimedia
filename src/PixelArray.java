import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * PixelArray: A class developed by Fahmi Ncibi, it contains methods to display Graphical array of data
 * Date: August 28, 2014
 * Published: July 1, 2017
 */
public class PixelArray extends JFrame{

	private static final long serialVersionUID = 1L;

	private String windowTitle=null; // Title of the window
	private JScrollPane scrollTable=null; // Scrollbar
	private JTable table=null; // graphical table that appears in the window, which will contain the values ​​of the pixel matrices
	private JPanel panelTable=null; // panel that will contain the pixel table
	int[][] pixelArray=null; // pixel matrix of the image in full size
	private String[][] pixelArrayStr=null; // string matrix that contains hexadecimal pixel color values
	private String[] titles=null; // the titles in the chart
	private int width; // the width of the image
	private int height; // the height of the image
	private Object[][] pixelObject; // an object that will contain the pixel matrix (integer or hexadecimal) useful for building the graphical array, since the JTable object takes an Object parameter

	/* Default constructor, empty*/
	public PixelArray(){
		super();
	}

	/* 2nd Constructor which takes in parameter the title of the window, a matrix which contains the hexadecimal values
	 * image colors, width and height of the image.
	 */
	public PixelArray(String windowTitle,String[][] pixelArrayStr,int width,int height){
		// call to the members of the mother class (JFrame)
		super();
		// initialization of the attributes of the class:
		this.windowTitle=windowTitle;
		this.width=width;
		this.height=height;
		this.pixelArrayStr=pixelArrayStr;
		// call to the method that will build the window and fill in the chart:
		init();
		setVisible(true); // shows the window on the screen
	}

	/* 3rd Constructor which takes in parameter the title of the window, a matrix which contains the integer values
	 * image colors, width and height of the image.
	 */
	public PixelArray(String windowTitle,int[][] pixelArray,int width,int height){
		//call to the members of the mother class (JFrame)
		super();
		//initialization of the attributes of the class:
		this.windowTitle=windowTitle;
		this.width=width;
		this.height=height;
		this.pixelArray=pixelArray;
		// call to the method that will build the window and fill in the chart:
		init();
		setVisible(true);
	}

	/*
	 * method that initializes the main window, fill in the chart
	 */
	private void init() {
		// Test on the matrix passed in parameter
		if(pixelArray!=null) // if the matrix passed is an integer matrix we make an integer conversion => Object, we call the conversion method with an integer matrix
			pixelObject=convertArray2Object(pixelArray,null,width,height);
		if(pixelArrayStr!=null)// if the matrix passed is a matrix of string of characters one makes a conversion string => object, one calls the method with a matrix of string
			pixelObject=convertArray2Object(null,pixelArrayStr,width,height);
		titles=new String[width]; //table that contains the titles of the graphic table JTable
		for(int i=0;i<width;i++)
			titles[i]=""+i;

		table=new JTable(pixelObject,titles); // creation of the JTable object
		panelTable=new JPanel(); // creation of the panel that will contain the graphic board
		panelTable.add(table); // adding the graphic board to the panel
		scrollTable=new JScrollPane(panelTable); //Add a scroll bar to the panel
		scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // the horizontal scroll bar is always visible
		scrollTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // the vertical scroll bar is always visible
		getContentPane().add(scrollTable); // adding the scroll bar to the window
		this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH); // the window opens in default screen color
		setTitle(windowTitle); // config the title of the window
		setSize(new Dimension(300,300)); // size of the window is 300x300
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// when the window closes, the window will be hidden

	}

	/* method that converts an integer matrix or string into an Object*/
	private Object[][] convertArray2Object(int[][] array,String[][] arrayStr,int width,int height) {
		Object[][] object=new Object[width+100][height+100]; // the object of return
		/* table run and fill the object matrix */
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				// Test if it is a matrix of integers or string of characters:
				if(array!=null)
					object[j][i]=new Integer(array[i][j]);
				else if(arrayStr!=null)
					object[j][i]=new String(arrayStr[i][j]);
			}
		}
		return object;
	}
}
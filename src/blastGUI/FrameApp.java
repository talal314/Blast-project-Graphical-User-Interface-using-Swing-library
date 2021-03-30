package blastGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import blast.BlastController;


public class FrameApp {
	
   private JFrame frame;
   private JLabel emptyLabel;
   private JLabel emptyLabel1;
   private JComboBox<String> searchComboBox;
   private JButton saveItem;
   private JTextArea textArea;
   private JTextField similaritypercentage;
   private BlastController bCnt = new BlastController();
   private JRadioButton type1,type2;
   private static final String dataBaseFile = new String("resources/yeast.aa");
   private static final String dataBaseIndexes = new String("resources/yeast.aa.indexs");
   private boolean exist;
	
	public void newFrame() {	
		frame = new JFrame("FameSearch");
		JPanel subPanel = new JPanel();
		emptyLabel= new JLabel("Please insert a sequence");
		emptyLabel1= new JLabel("Please insert a value betwen 0.0 untill 1.0");
		searchComboBox = new JComboBox<>();
		saveItem = new JButton("Save Item");
		textArea = new JTextArea(5,20);
		similaritypercentage = new JTextField("",5);
		type1=new JRadioButton("p", true);
		type2=new JRadioButton("n");
		ButtonGroup group = new ButtonGroup();
		group.add(type1);
		group.add(type2);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		searchComboBox.setEditable(true);	
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(380, 100));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setLineWrap(true);
		textArea.setEditable(true);
		
	
		
		saveItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Get the type of the the query and set the text null every time we need to make a new search
				char typeOfQuery=getTypeOfQuery();
				textArea.setText("");
				//try catch with and NumberFormatException just in case if the user didn´t insert a valid float number
				try {
					float similaritypercent=Float.parseFloat(similaritypercentage.getText());
					
					if(getSelectedITem()!=null && similaritypercent>=0 && similaritypercent<=1.0) {		
			      		String selectedItem=searchComboBox.getSelectedItem().toString();
						textArea.append(blastController(typeOfQuery,selectedItem,similaritypercent));
						
						//check if item is exist in searchComboBox, if not exist add item
						if(checkItemIfExist(searchComboBox,selectedItem)==false) {
							searchComboBox.addItem(selectedItem);
						}else {
							textArea.append(" Item is already exist!");
						}
					}
					//if the seq is a null or similarity percent is not between(0.0,0.1) show this message
					else {
						textArea.append("Please insert a valid sequence or a valid value of similarity percentage!");
					}
			
				}
				catch(NumberFormatException exp){
					textArea.append("Sequence or similarity percent value is not correct!!");
				}
				
				
				
			}
		});
		
		
		//subPanel to add more than one component and then add it to the frame in the center borderlayout
		
		subPanel.add(type1);
		subPanel.add(type2);
		subPanel.add(emptyLabel1);
		subPanel.add(similaritypercentage);
		
		
		frame.getContentPane().add(subPanel,BorderLayout.CENTER);
		frame.getContentPane().add(searchComboBox, BorderLayout.WEST);
		frame.getContentPane().add(emptyLabel, BorderLayout.NORTH);
		frame.getContentPane().add(saveItem, BorderLayout.EAST);
		frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);	
		frame.pack();
		frame.setVisible(true);
	}
	
	//Check if the item is exist in comboBox
	public boolean checkItemIfExist(JComboBox<String> comboBox,String newItem){
		//if there is more than one item then check if the the item is exist return true
		if(comboBox.getItemCount()>=1) {
		for(int c=0;c<comboBox.getItemCount();c++) {		
	     	if(comboBox.getItemAt(c).toString()==newItem) {
				exist= true;
				break;
			}
			else{
				exist= false;
			}
		}}
		return exist;
	
	}
	
	//Method to get the selected item in the searchComboBox
	public String getSelectedITem(){
		if(searchComboBox.getSelectedItem()!=null) {
			return searchComboBox.getSelectedItem().toString();
		}
		else {
			return null;
		}
	}
	
	//Method to get the result of the query in String format
	public String blastController(char t,String Seq,float similarityper) {
		try{
			String result = bCnt.blastQuery(t, dataBaseFile, 
					dataBaseIndexes, similarityper, Seq);
			return result;
		} catch(Exception exc){
			return "Error en la llamada: " + exc.toString() ;
		}
		
	}
	
	
	//Method to get type of query from Radio Button
	public char getTypeOfQuery() {
		if(type1.isSelected()) {
			return 'p';
		}
		else {	
			return 'n';
		}
	}
	
	
	
}

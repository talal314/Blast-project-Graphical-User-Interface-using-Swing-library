package blastGUI;

import blast.BlastController;

public class BlastNoGUIMain {
	
	private static final String dataBaseFile = new String("resources/yeast.aa");
	private static final String dataBaseIndexes = new String("resources/yeast.aa.indexs");
	
	public static void main(String args[]){
		BlastController bCnt = new BlastController();
		try{
			String result = bCnt.blastQuery('p', dataBaseFile, 
					dataBaseIndexes, (float) 0.9, "GKGKGKGKGK");
			System.out.println(result);
		} catch(Exception exc){
			System.out.println("Error en la llamada: " + exc.toString());
		}
		
		FrameApp frameApp = new FrameApp();
		frameApp.newFrame();
	
	}
}
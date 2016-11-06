package org.dunkentec.xfdf2csvconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A simple http://logging.apache.org/log4j/2.x demo,
 *  see file log4j2.xml for configuration options.
 * 
 */
public class App extends Application{
    private static Logger log = LogManager.getLogger(App.class);

    
    /**
     * @param args Unused
     */
    public static void main( String[] args ) {
    		try{
    			launch(args);
    		}catch(Exception e){
    			log.error("Error: ", e);
    		}
    }



	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ConverterGui.fxml"));
		
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("xfdf2csv Converter");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}

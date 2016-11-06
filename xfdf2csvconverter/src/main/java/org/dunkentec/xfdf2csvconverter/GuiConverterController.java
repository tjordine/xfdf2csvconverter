package org.dunkentec.xfdf2csvconverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

public class GuiConverterController implements Initializable{
	
	private static Logger log = LogManager.getLogger(GuiConverterController.class);
	
	@FXML
	private Parent root;
	
	@FXML
	private RadioButton rbSingleFile, rbFolder;
	
	private ToggleGroup modeGroup;
	
	@FXML
	private Label lbFilePath, lbDirPath, lbOutPath, lbStatus;
	
	@FXML
	private TextField tfFilePath, tfDirPath, tfOutPath;
	
	@FXML
	private Button btOpenFile, btOpenDir, btOutDir, btConvert, btQuit;
	
	private enum Mode{fileMode, dirMode};
	
	private Mode currentMode = Mode.fileMode;
	
	private Converter converter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		converter = new Converter();	
		modeGroup = new ToggleGroup();
		
		rbSingleFile.setToggleGroup(modeGroup);
		rbFolder.setToggleGroup(modeGroup);
		
		toggleModes();
		
	}
	
	@FXML
	public void toggleModeRadioButtons(ActionEvent event){
		if(event.getSource() == rbSingleFile){
			currentMode = Mode.fileMode;
			log.debug("Switching to file mode");
		}
		
		if(event.getSource() == rbFolder){
			currentMode = Mode.dirMode;
			log.debug("Switching to directory mode");
		}
		
		toggleModes();
	}
	
	@FXML
	public void openFileChooser(ActionEvent event){		
		if(event.getSource() == btOpenFile){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("xfdf-Datei auswählen");
			fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("xfdx", "*.xfdf", "*.XFDF")
			);
			File xfdfFile = fileChooser.showOpenDialog(root.getScene().getWindow());
			if(xfdfFile != null){
				tfFilePath.setText(xfdfFile.getAbsolutePath());
				tfOutPath.setText(xfdfFile.getParentFile().getAbsolutePath());
			}
		}
		
		if(event.getSource() == btOpenDir){
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setTitle("Ordner mit xfdf-Dateien auswählen");
			File xfdfDir = dirChooser.showDialog(root.getScene().getWindow());
			if(xfdfDir != null){
				tfDirPath.setText(xfdfDir.getAbsolutePath());
				tfOutPath.setText(xfdfDir.getAbsolutePath());
			}
		}
		
		if(event.getSource() == btOutDir){
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setTitle("Ausgabe-Ordner wählen");
			File outDir = dirChooser.showDialog(root.getScene().getWindow());
			if(outDir != null){
				tfOutPath.setText(outDir.getAbsolutePath());
			}
		}
			
	}
	
	@FXML
	public void convert(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		lbStatus.setText("Status: Konvertiere...");
		
		if(currentMode == Mode.fileMode){			
			String inFile = tfFilePath.getText();
			String outDir = tfOutPath.getText();
			
			if(inFile.equals("") || outDir.equals("")){
				return;
			}
			
			converter.convertSingleFileXfdfToCsv(inFile, outDir);
		}else if(currentMode == Mode.dirMode){
			String inDir = tfDirPath.getText();
			String outDir = tfOutPath.getText();
			
			if(inDir.equals("") || outDir.equals("")){
				return;
			}
			
			converter.convertXfdfToCsv(inDir, outDir);
		}
		
		lbStatus.setText("Status: Konvertierung abgeschlossen.");
	}
	
	@FXML
	public void quit(ActionEvent event){
		System.exit(0);
	}
	
	private void toggleModes(){
		if(currentMode == Mode.fileMode){
			lbDirPath.setDisable(true);
			tfDirPath.setDisable(true);
			btOpenDir.setDisable(true);
			
			lbFilePath.setDisable(false);
			tfFilePath.setDisable(false);
			btOpenFile.setDisable(false);
		}else if(currentMode == Mode.dirMode){
			lbDirPath.setDisable(false);
			tfDirPath.setDisable(false);
			btOpenDir.setDisable(false);
			
			lbFilePath.setDisable(true);
			tfFilePath.setDisable(true);
			btOpenFile.setDisable(true);
		}
	}
	

}

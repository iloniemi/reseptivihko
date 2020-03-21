package fxReseptivihko;
	
import javafx.application.Application;
import javafx.stage.Stage;
import reseptivihko.Reseptivihko;
import reseptivihko.VirheellinenSyottotietoException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Juho
 * @version 27 Feb 2020
 *
 */
public class ReseptivihkoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("ReseptivihkoGUIView.fxml"));
		    final Pane root = (Pane)loader.load();
		    final ReseptivihkoGUIController controllerGUI = (ReseptivihkoGUIController) loader.getController();

		    final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("reseptivihko.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Reseptivihko");
			
			
			Reseptivihko vihko = new Reseptivihko();
			vihko.asetaKansio("oma");
			controllerGUI.setVihko(vihko);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

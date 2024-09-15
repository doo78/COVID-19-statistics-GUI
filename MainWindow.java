import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.scene.Parent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



/**
 * Write a description of JavaFX class MainWindow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainWindow extends Application
{
    @FXML private Label errorLabel;
    @FXML private DatePicker startDateBox;
    @FXML private DatePicker endDateBox;
    
    @FXML private AnchorPane mainWindow;
    private String[] panes = {"welcome.fxml", "map.fxml", "statistics.fxml", "linechart.fxml"};
    private int currentPane;

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
        Pane root = loader.load();
        
        MainWindow controller = loader.getController();
        AnchorPane mainWindowPane = controller.mainWindow;
        
        VBox pane = FXMLLoader.load(getClass().getResource(panes[currentPane]));
        mainWindowPane.getChildren().setAll(pane);
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Covid Tracker");
        stage.setScene(scene);
        
        // Show the Stage (window)
        stage.show();
        
    }
    
    /**
     * Upon trying to leave the page, this method will run and determine
     * whether the selected date range is valid. There are multiple different
     * errors that can occur here, so a label is used to display the exact
     * problem to the user.
     */
    private boolean noDateErrors(){
        //obtain chosen dates
        LocalDate startDate = startDateBox.getValue();
        LocalDate endDate = endDateBox.getValue();
        //set maximum and minimum dates provided in csv data
        LocalDate maxDate = LocalDate.of(2023, 2, 9);
        LocalDate minDate = LocalDate.of(2020, 2, 14);
        
        //date pickers are empty
        if(startDate == null || endDate == null){
            errorLabel.setText("Please choose a date range!");
            return false;
        }
        
        //dates too early
        if(startDate.isBefore(minDate) || endDate.isBefore(minDate)){
            errorLabel.setText("Dates must be after February 13th, 2020.");
            return false;
        }
        
        //dates too late
        if(startDate.isAfter(maxDate) || endDate.isAfter(maxDate)){
            errorLabel.setText("Dates must be before February 10th, 2023.");
            return false;
        }
        
        //chosen end date is before start date
        if(startDate.isAfter(endDate)){
            errorLabel.setText("The start date must be before the end date!");
            return false;
        }
        
        //if code reaches here, there are no errors!
        errorLabel.setText("");
        return true;
    }
    
    /**
     * Returns the date in a specified DatePicker as a formatted String.
     * This formatted string is the same as in the csv data.
     * 
     * @param dateSelection The DatePicker to read from.
     */
    private String dateToString(DatePicker dateSelection){
        LocalDate date = dateSelection.getValue();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-M-d");
        return date.format(format);
    }
    
    /**
     * Uses FXMLLoader to load the specified page into a smaller pane for the
     * main application.
     * Once this is done, create a specific controller to set the start and end
     * dates to use when loading the data.
     * 
     * @param page The page number to load, based on the panes array.
     */
    private void loadPage(int page) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(panes[page]));          
        VBox pane = fxmlLoader.load(); //load fxml into the pane
        
        //load controller based on the page's control class
        if(page == 1){ //map
            MapPanel controlMap = fxmlLoader.<MapPanel>getController();
            controlMap.setStartDate(dateToString(startDateBox));
            controlMap.setEndDate(dateToString(endDateBox));
            controlMap.generateAllData();
        }else if(page == 2){ //statistics
            StatisticsPanel controlStats = fxmlLoader.<StatisticsPanel>getController();
            controlStats.setStartDate(dateToString(startDateBox));
            controlStats.setEndDate(dateToString(endDateBox));
        }else if(page == 3){ //line chart
            LineChartPanel controlChart = fxmlLoader.<LineChartPanel>getController();
            controlChart.setStartDate(dateToString(startDateBox));
            controlChart.setEndDate(dateToString(endDateBox));
        }
        //page 0 has no controller as it has nothing to change

        mainWindow.getChildren().setAll(pane); //display pane
    }
    
    //EVENTS
    @FXML
    public void buttonNext(ActionEvent event) throws IOException
    {
        if(noDateErrors()){
            if (currentPane == 3){
                currentPane = 0;
            }
            else{
                currentPane += 1;
            }
        }//if dates are invalid, send user to welcome page to avoid loading data
        else{
            currentPane = 0;
        }
        
        loadPage(currentPane);
    }
    
    @FXML
    public void buttonPrev(ActionEvent event) throws IOException
    {
        if(noDateErrors()){
            if (currentPane == 0){
                currentPane = 3;
            }
            else{
                currentPane -= 1;
            }
        }//if dates are invalid, send user to welcome page to avoid loading data
        else{
            currentPane = 0;
        }
        
        loadPage(currentPane);
    }
}

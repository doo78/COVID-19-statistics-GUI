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
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Map of London Boroughs with their total deaths over the specified period of time.
 *
 * @Danny Tierney k23081869
 * @27/03/2024
 */
public class MapPanel
{
    //initialise every label and radio button
    @FXML private Label labelBARN;
    @FXML private RadioButton selectBARN;
    @FXML private Label labelHRGY;
    @FXML private RadioButton selectHRGY;
    @FXML private Label labelENFI;
    @FXML private RadioButton selectENFI;
    @FXML private Label labelWALT;
    @FXML private RadioButton selectWALT;
    @FXML private Label labelHRRW;
    @FXML private RadioButton selectHRRW;
    @FXML private Label labelBREN;
    @FXML private RadioButton selectBREN;
    @FXML private Label labelCAMD;
    @FXML private RadioButton selectCAMD;
    @FXML private Label labelISLI;
    @FXML private RadioButton selectISLI;
    @FXML private Label labelHACK;
    @FXML private RadioButton selectHACK;
    @FXML private Label labelREDB;
    @FXML private RadioButton selectREDB;
    @FXML private Label labelHAVE;
    @FXML private RadioButton selectHAVE;
    @FXML private Label labelHILL;
    @FXML private RadioButton selectHILL;
    @FXML private Label labelEALI;
    @FXML private RadioButton selectEALI;
    @FXML private Label labelKENS;
    @FXML private RadioButton selectKENS;
    @FXML private Label labelWSTM;
    @FXML private RadioButton selectWSTM;
    @FXML private Label labelTOWH;
    @FXML private RadioButton selectTOWH;
    @FXML private Label labelNEWH;
    @FXML private RadioButton selectNEWH;
    @FXML private Label labelBARK;
    @FXML private RadioButton selectBARK;
    @FXML private Label labelHOUN;
    @FXML private RadioButton selectHOUN;
    @FXML private Label labelHAMM;
    @FXML private RadioButton selectHAMM;
    @FXML private Label labelWAND;
    @FXML private RadioButton selectWAND;
    @FXML private Label labelCITY;
    @FXML private RadioButton selectCITY;
    @FXML private Label labelGWCH;
    @FXML private RadioButton selectGWCH;
    @FXML private Label labelBEXL;
    @FXML private RadioButton selectBEXL;
    @FXML private Label labelRICH;
    @FXML private RadioButton selectRICH;
    @FXML private Label labelMERT;
    @FXML private RadioButton selectMERT;
    @FXML private Label labelLAMB;
    @FXML private RadioButton selectLAMB;
    @FXML private Label labelSTHW;
    @FXML private RadioButton selectSTHW;
    @FXML private Label labelLEWS;
    @FXML private RadioButton selectLEWS;
    @FXML private Label labelKING;
    @FXML private RadioButton selectKING;
    @FXML private Label labelSUTT;
    @FXML private RadioButton selectSUTT;
    @FXML private Label labelCROY;
    @FXML private RadioButton selectCROY;
    @FXML private Label labelBROM;
    @FXML private RadioButton selectBROM;
    
    //toggle group of all radio buttons
    @FXML private ToggleGroup locations;
    
    //button to open stats page
    @FXML private Button statsButton;
    
    //start and end dates
    private String startDate;
    private String endDate;
    
    //to fetch data
    private CovidDataLoader dataLoad = new CovidDataLoader();
    private ArrayList<CovidData> covidData = dataLoad.load();
    
    //links shortened 4 letter names to the full name of each borough
    //set up in generateAllData()
    private HashMap<RadioButton, String> boroughFullNames = new HashMap<>();
    
    //used in the generateAllData() and assignData() methods.
    //stores all indexes of data within the date range.
    private ArrayList<Integer> dateIndexes = new ArrayList<>();
    

    
    /** 
     * Runs through every borough, setting the displayed total deaths and
     * the label's colour representation.
     */
    public void generateAllData(){
        //Reset data assuming a new date range is picked.
        boroughFullNames.clear();
        dateIndexes.clear();
        
        /*Goes through entire list of data, noting down every index of data
         *that has a valid date. This is done to avoid going through all
         *the data for every Borough.
         */
        for(int i = 0; i < covidData.size(); i++){
            if(covidData.get(i).inDateRange(startDate, endDate)){
                dateIndexes.add(i); //ArrayList initialized above.
                //stores current index if the date is valid.
            }
        }
        
        //For each borough:
        //Generate its Label.
        //Assign the RadioButton to its full name.
        assignData(labelBARN, selectBARN, "Barnet");
        assignData(labelHRGY, selectHRGY, "Haringey");
        assignData(labelENFI, selectENFI, "Enfield");
        assignData(labelWALT, selectWALT, "Waltham Forest");
        assignData(labelHRRW, selectHRRW, "Harrow");
        assignData(labelBREN, selectBREN, "Brent");
        assignData(labelCAMD, selectCAMD, "Camden");
        assignData(labelISLI, selectISLI, "Islington");
        assignData(labelHACK, selectHACK, "Hackney");
        assignData(labelREDB, selectREDB, "Redbridge");
        assignData(labelHAVE, selectHAVE, "Havering");
        assignData(labelHILL, selectHILL, "Hillingdon");
        assignData(labelEALI, selectEALI, "Ealing");
        assignData(labelKENS, selectKENS, "Kensington And Chelsea");
        assignData(labelWSTM, selectWSTM, "Westminster");
        assignData(labelTOWH, selectTOWH, "Tower Hamlets");
        assignData(labelNEWH, selectNEWH, "Newham");
        assignData(labelBARK, selectBARK, "Barking And Dagenham");
        assignData(labelHOUN, selectHOUN, "Hounslow");
        assignData(labelHAMM, selectHAMM, "Hammersmith And Fulham");
        assignData(labelWAND, selectWAND, "Wandsworth");
        assignData(labelCITY, selectCITY, "City Of London");
        assignData(labelGWCH, selectGWCH, "Greenwich");
        assignData(labelBEXL, selectBEXL, "Bexley");
        assignData(labelRICH, selectRICH, "Richmond Upon Thames");
        assignData(labelMERT, selectMERT, "Merton");
        assignData(labelLAMB, selectLAMB, "Lambeth");
        assignData(labelSTHW, selectSTHW, "Southwark");
        assignData(labelLEWS, selectLEWS, "Lewisham");
        assignData(labelKING, selectKING, "Kingston Upon Thames");
        assignData(labelSUTT, selectSUTT, "Sutton");
        assignData(labelCROY, selectCROY, "Croydon");
        assignData(labelBROM, selectBROM, "Bromley");
    }
    
    /** 
     * Fetches the data for a specified borough, calculating the total deaths
     * over a period of time and then displaying it on the corresponding
     * label.
     * 
     * @param boroughLabel The label object to be modified.
     * @param boroughName The full name of the borough found in the csv.
     */
    private void assignData(Label label, RadioButton select, String boroughName){
        //Link the full borough name to its RadioButton.
        boroughFullNames.put(select, boroughName);
        //This HashMap is used in Event methods below.
        
        //determine total deaths over selected date period for this borough
        int totalDeaths = 0;
        String checkBorough;
        for (int index : dateIndexes){
            checkBorough = covidData.get(index).getBorough();
            if(checkBorough.equals(boroughName)){
                totalDeaths += covidData.get(index).getNewDeaths();
            }
        }
        
        //set label text display
        label.setText("+" + String.valueOf(totalDeaths));
        
        //divide total valid date indexes by 33 (number of boroughs)
        int totalDays = dateIndexes.size() / 33;
        
        //set label colour depending on deaths per day
        if(totalDeaths > (totalDays / 2)){ //>0.5 deaths a day
            label.setTextFill(Color.color(1, 0, 0)); //red
        }else if(totalDeaths > (totalDays / 5)){ //>0.2 deaths a day
            label.setTextFill(Color.color(1, 0.6, 0)); //orange
        }else if(totalDeaths > 0){ //>0 deaths a day
            label.setTextFill(Color.color(0.8, 0.8, 0)); //dim yellow
        }else{ //no deaths in selected time period
            label.setTextFill(Color.color(0, 1, 0)); //lime green
        }
    }
    
    /**
     * Set the start date.
     * 
     * @param date The formatted string date
     */
    public void setStartDate(String date)
    {
        startDate = date;
    }
    
    /**
     * Set the end date.
     * 
     * @param date The formatted string date
     */
    public void setEndDate(String date)
    {
        endDate = date;
    }
    
    //EVENTS
    
    /** 
     * Triggers when 'statsButton' in the bottom right is clicked.
     */
    @FXML
    private void openStats(ActionEvent event)
    {
        //obtain full borough name from hashmap
        RadioButton selected = (RadioButton)locations.getSelectedToggle();
        String boroughFullName = boroughFullNames.get(selected);
            
        //open new window
        BoroughStatsWindow newWindow = new BoroughStatsWindow(covidData, 
                        dateIndexes, boroughFullName, startDate, endDate);
    }
    
    /** 
     * Triggers when a radio button is selected.
     */
    @FXML
    void locationSelected(ActionEvent event) {
        //Updates the button to display the selected borough name.
        RadioButton selected = (RadioButton)locations.getSelectedToggle();
        statsButton.setText("Statistics for " + boroughFullNames.get(selected));
    }
}

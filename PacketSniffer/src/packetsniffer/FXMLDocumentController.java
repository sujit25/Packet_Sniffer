/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetsniffer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//import javax.swing.event.ChangeListener;


/**
 *
 * @author sujit
 */
public class FXMLDocumentController implements Initializable 
{
    int count=0;
    /*
    @FXML
    private Label label;
    */
    /*
    @FXML
    private Button myButton;
    */
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private ComboBox<String> interfaceComboBox;
    @FXML
    private TableView<PacketData> viewTable;
    
    @FXML
    private TableColumn<PacketData,String> sourceIPColumn;
    @FXML
    private TableColumn<PacketData,String> destinationIPColumn;
    @FXML
    private TableColumn<PacketData,String> sourceMacColumn;
    @FXML
    private TableColumn<PacketData,String> destinationMacColumn;
    @FXML
    private TableColumn<PacketData,String> packetLengthColumn;       
    
    /*
    @FXML
    private TableColumn<String,String> sourceIpColumn;
    @FXML
    private TableColumn<String,String> destIpColumn;
    @FXML
    private TableColumn<String,String> sourceMacColumn;
    @FXML
    private TableColumn<String,String> destMacColumn;
    @FXML
    private TableColumn<String,String> packetLength;
    */        
    private Sniffer s;
    private String deviceToSniff;
    private boolean continueSniffing;
    //private List<PacketData> packetInfo;
    private List<String> packData;
    private ObservableList<PacketData> packetInfoList;
    /*
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        System.out.println("You clicked me!");
        label.setText("Hello World!");
                
    }
    */
    /*
    @FXML
    private void addEntryToTable(ActionEvent Event)
    {   
        while(true){
            if(count<10){
        packetInfoList.add(new PacketData("sujit","sujit","sujit","sujit","sujit"));
        viewTable.setItems(packetInfoList);
            }
        count++;
        }
    }
    */
    @FXML
    private void startSniffing(ActionEvent Event)
    {
        System.out.println("clicked on start button!!!");
        continueSniffing=true;
        Parallel p1= new Parallel();
        viewTable.setItems(FXCollections.observableArrayList(new PacketData("","","","","")));
        packetInfoList.clear();
        p1.start();
        /*
        try
        {    
            p1.join();
        }
        catch(Exception e)
        {   
            System.out.println("unable to join child thread to main thread");
        } 
        */
        /*
        while(continueSniffing)
        {
            packData=s.capturePackets(deviceToSniff);
            if(packData!= null && !packData.isEmpty())
            {   
                System.out.println("-----------");
                System.out.println("Source ip:"+packData.get(0));
                System.out.println("Destination ip:"+ packData.get(1));
                System.out.println("Source Mac:"+ packData.get(2));
                System.out.println("Destination Mac:"+ packData.get(3));
                System.out.println("Packet Length:"+ packData.get(4));
                System.out.println("------------");
                
                  
                packetInfoList.add(new PacketData(packData.get(0),packData.get(1),packData.get(2),packData.get(3),packData.get(4)));
                //packetInfoList.add(new PacketData("sujit","sujit","sujit","sujit","sujit"));
                //ObservableList<PacketData> packetInfoList = FXCollections.observableArrayList(new PacketData("sujit","sujit","sujit","sujit","sujit"));
                
              //  packetInfo.add(new PacketData(packData.get(0),packData.get(1),packData.get(2),packData.get(3),packData.get(4)));
              //this.addEntryToTable(new ActionEvent());
                count++;
            }
            //viewTable.setItems(packetInfoList);
            viewTable.setItems(packetInfoList);
            /*
            System.out.println("-----------");
            System.out.println("Source ip:"+packetInfo.get(0));
            System.out.println("Destination ip:"+ packetInfo.get(1));
            System.out.println("Source Mac:"+ packetInfo.get(2));
            System.out.println("Destination Mac:"+ packetInfo.get(3));
            System.out.println("Packet Length:"+ packetInfo.get(4));
            System.out.println("------------");
            //
            //packetInfoList= FXCollections.observableList(packetInfo);
           //viewTable.setItems(packetInfoList);
            if(count>10)
                   break;
        } 
        */
    }
    
    @FXML
    private void stopSniffing(ActionEvent Event)
    {
        continueSniffing=false;
        System.out.println("Sniffing Stopped Successfully!!");
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        continueSniffing=true;
        s= new Sniffer();
        interfaceComboBox.getItems().addAll(s.getInterfaceList());
        //deviceToSniff=interfaceComboBox.getSelectionModel().getSelectedItem();
        interfaceComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
           public void changed(ObservableValue<? extends String> observable,String  
            oldValue,String newValue) 
           {
               deviceToSniff=newValue;
                System.out.println("selected device is:"+deviceToSniff);
           } 
        });
                
        /*
        this.sourceIpColumn.setSortable(false);
        this.destIpColumn.setSortable(false);
        this.sourceMacColumn.setSortable(false);
        this.destMacColumn.setSortable(false);
        this.packetLength.setSortable(false);
        viewTable.setEditable(true);
        this.sourceIpColumn.setCellValueFactory(new PropertyValueFactory<String,String>(new String()));
        this.destIpColumn.setCellValueFactory(new PropertyValueFactory<String,String>(new String()));
        this.sourceMacColumn.setCellValueFactory(new PropertyValueFactory<String,String>(new String()));
        this.destMacColumn.setCellValueFactory(new PropertyValueFactory<String,String>(new String()));
        this.packetLength.setCellValueFactory(new PropertyValueFactory<String,String>(new String()));
        */
                
        //ObservableList<String> data= FXCollections.observableArrayList("sujit","sujit1","sujit2","sujit3","sujit4");
        //viewTable.setItems(data);
        /*
        this.sourceIP =new TableColumn("SourceIP");
        this.destIp =new TableColumn("destIp");
        this.srcMac =new TableColumn("Sourcemac");
        this.destIp =new TableColumn("DestinationMac");
        this.packLength =new TableColumn("packetLength");
        this.viewTable.getColumns().addAll(this.sourceIP,this.destIp,this.srcMac,this.destIp,this.packLength);
        
        sourceIP.setCellValueFactory(new PropertyValueFactory<String,String>("sujit"));
        destIp.setCellValueFactory(new PropertyValueFactory<String,String>("sujit"));
        srcMac.setCellValueFactory(new PropertyValueFactory<String,String>("sujit"));
        destIp.setCellValueFactory(new PropertyValueFactory<String,String>("sujit"));
        packLength.setCellValueFactory(new PropertyValueFactory<String,String>("sujit"));
       ObservableList<String> data= FXCollections.observableArrayList(new String("sujit"),new String("sujit"),new String("sujit"),new String("sujit"),new String("sujit"));
        //viewTable.setItems(data);
        */
        
        viewTable.setEditable(true);
        
        sourceIPColumn.setCellValueFactory(new PropertyValueFactory<PacketData,String>("sourceIP"));
        destinationIPColumn.setCellValueFactory(new PropertyValueFactory<PacketData,String>("destIP"));
        sourceMacColumn.setCellValueFactory(new PropertyValueFactory<PacketData,String>("sourceMac"));
        destinationMacColumn.setCellValueFactory(new PropertyValueFactory<PacketData,String>("destMac"));
        packetLengthColumn.setCellValueFactory(new PropertyValueFactory<PacketData,String>("packetLength"));
        //ObservableList<PacketData> data= FXCollections.observableArrayList(new PacketData("sujit1","sujit2","sujit3","sujit4","sujit5"));
        //viewTable.setItems(data);
        
        /*
        ObservableList<PacketData> data= FXCollections.observableArrayList(new PacketData("sujit","sujit","sujit","sujit","sujit"));
        viewTable.setItems(data);
        */
        /*
        for(int i=0;i<100;i++)
        {
           viewTable.setItems(data);
           data.add(new PacketData("sujit"+i,"sujit"+i,"sujit"+i,"sujit"+i,"sujit"+i));
        }
        */
        packetInfoList=FXCollections.observableArrayList(new PacketData("Sujit","Sujit","Sujit","Sujit","Sujit"));
        
    }
                
    public class PacketData 
   {
        private SimpleStringProperty sourceIP;
        private SimpleStringProperty destIP;
        private SimpleStringProperty sourceMac;
        private SimpleStringProperty destMac;
        private SimpleStringProperty packetLength;
        
        private PacketData(String srcIP,String dstIP, String srcMac, String dstMac,String packLen)
        {
            this.sourceIP= new SimpleStringProperty(srcIP);
            this.destIP = new SimpleStringProperty(dstIP);
            this.sourceMac = new SimpleStringProperty(srcMac);
            this.destMac= new SimpleStringProperty(dstMac);
            this.packetLength = new SimpleStringProperty(packLen);
        }
        //methods for SourceIp        
        public String getSourceIP()
        {
            return sourceIP.get();
        }
        public void setSourceIP(String srcIp)
        {
            sourceIP.set(srcIp);
        }
        public StringProperty sourceIPPeroperty()
        {
            return sourceIP;
        }
        
        //methods for DestinationIp
        
        public String getDestIp()
        {
            return destIP.get();
        }
        public void setDestIp(String dstIp)
        {
            destIP.set(dstIp);
        }
        public StringProperty destIPProperty()
        {
            return destIP;
        }
        
        //methods for SourceMac
        public String getSourceMac()
        {
            return sourceMac.get();
        }       
        public void setSourceMac(String srcMac)
        {
            sourceMac.set(srcMac);
        }
        public StringProperty sourceMacProperty()
        {
            return sourceMac;
        }
        
        //methods for DestinationMac
        public String getDestMac()
        {
            return destMac.get();
        }
        public void setDestMac(String destinationMac)
        {
            destMac.set(destinationMac);
        }
        public StringProperty destMacProperty()
        {
            return destMac;
        }
        
        //methods for PacketLength
        public String getPacketLength()
        {
            return packetLength.get();
        }
        public void setPacketLength(String packLen)
        {
            packetLength.set(packLen);
        }
        public StringProperty packetLengthProperty()
        {
            return packetLength;
        }
    }
    
    public class Parallel extends Thread
    {
        public void run()
        {
            while(continueSniffing)
            {
                packData=s.capturePackets(deviceToSniff);
                if(packData!= null && !packData.isEmpty())
                {   
                    System.out.println("-----------");
                    System.out.println("Source ip:"+packData.get(0));
                    System.out.println("Destination ip:"+ packData.get(1));
                    System.out.println("Source Mac:"+ packData.get(2));
                    System.out.println("Destination Mac:"+ packData.get(3));
                    System.out.println("Packet Length:"+ packData.get(4));
                    System.out.println("------------");
                    
                    packetInfoList.add(new PacketData(packData.get(0),packData.get(1),packData.get(2),packData.get(3),packData.get(4)));
                    //packetInfoList.add(new PacketData("sujit","sujit","sujit","sujit","sujit"));
                    //ObservableList<PacketData> packetInfoList = FXCollections.observableArrayList(new PacketData("sujit","sujit","sujit","sujit","sujit"));

                  //  packetInfo.add(new PacketData(packData.get(0),packData.get(1),packData.get(2),packData.get(3),packData.get(4)));
                  //this.addEntryToTable(new ActionEvent());
                }
                //viewTable.setItems(packetInfoList);
                viewTable.setItems(packetInfoList);
                /*
                System.out.println("-----------");
                System.out.println("Source ip:"+packetInfo.get(0));
                System.out.println("Destination ip:"+ packetInfo.get(1));
                System.out.println("Source Mac:"+ packetInfo.get(2));
                System.out.println("Destination Mac:"+ packetInfo.get(3));
                System.out.println("Packet Length:"+ packetInfo.get(4));
                System.out.println("------------");
                */
                //packetInfoList= FXCollections.observableList(packetInfo);
                //viewTable.setItems(packetInfoList);
            }
        }
                
    }
     
}

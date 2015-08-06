/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetsniffer;

/**
 *
 * @author sujit
 */
import java.util.*;
import org.jnetpcap.*;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
/**
 *
 * @author sujit
 */
class Sniffer 
{
    private final List<PcapIf> interfacelist;
    private StringBuilder errbuf;
    /*
    private String sourceIp;
    private String destinationIp;
    private String sourceMac;
    private String destinationMac;
    private String packetLength;
    */
    private static List<String> packetInfo;
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        // TODO code application logic here
        Sniffer t= new TestSniffer();
        int r= Pcap.findAllDevs(t.interfacelist,t.errbuf);
        if(r!=Pcap.OK)
        System.out.println("error in printing device information");
        else
        {
            System.out.println("devices found!!!");
            t.printDeviceInformation(t.interfacelist);
            t.capturePackets();
        }
        
    }
    */
    public List<String> getInterfaceList()
    {
        int r= Pcap.findAllDevs(this.interfacelist,this.errbuf);
        if(r!=Pcap.OK)
        {
            System.out.println("No deives found!!!");
            return null;
        }    
        else
        {
           List<String> interfaceNames = new ArrayList<String>();
          for(PcapIf device: this.interfacelist)
          {
              interfaceNames.add(device.getName());
          }
            return interfaceNames;
        }
    }
    
    public  void printDeviceInformation(List<PcapIf> devList)
    {
        int i=0;
        for(PcapIf device: devList )
        {
            System.out.println("device"+ i+" information ");
            System.out.println(device.getName()+"\t"+device.getDescription());
        }
    }
    
    public List<String> capturePackets(String deviceToSniff)
    {
        //System.out.println("Enter the name of interface??");
        //int i= new  Scanner(System.in).nextInt();
        //PcapIf deviceToSniff= interfacelist.get(i);
        int snapLen = 64*1024;
        int flags= Pcap.MODE_PROMISCUOUS;
        int timeOut=10*100;
        Pcap pcap= Pcap.openLive(deviceToSniff, snapLen, flags, timeOut, errbuf);
        
        if(pcap!=null)
        {
            System.out.println("Successfullly opened"+ deviceToSniff+" for sniffing");
            PcapPacketHandler<String> jpacketHandler;
            jpacketHandler = new PcapPacketHandler<String>() {
                //Arp arp = new Arp();
                Tcp tcp= new Tcp();
                Ip4 ip=new Ip4(); 
                Ethernet e= new Ethernet();
                
                @Override
                public void nextPacket(PcapPacket packet, String user)
                {
                    if(packet.hasHeader(e))
                    {        
                        if (packet.hasHeader(ip)) 
                        {
                            if(packet.hasHeader(tcp))
                            {
                               // System.out.println("inside nextPacket Method");
                                /*
                                System.out.println("Source Ip:"+ FormatUtils.ip(ip.source()));
                                System.out.println("Destination Ip:" + FormatUtils.ip(ip.destination()));
                                System.out.println("Source Mac Address:" + FormatUtils.mac(e.source()));
                                System.out.println("Destination Mac Address:" + FormatUtils.mac(e.destination()));
                                System.out.println("Time of capture:"+ new Date().getTime());
                                System.out.println("length of packet:"+ e.getLength());
                                System.out.println("");
                                */
                                packetInfo.clear();
                                packetInfo.add(0,FormatUtils.ip(ip.source()) );
                                packetInfo.add(1,FormatUtils.ip(ip.destination()));
                                packetInfo.add(2,FormatUtils.mac(e.source()));
                                packetInfo.add(3,FormatUtils.mac(e.destination()));
                                packetInfo.add(4,Integer.toString(e.getLength()));
                                
                            }    
                             // System.out.println();
                        }    
                    }
                }
            };
            //we enter the loop and capture the 10000 packets here.You can  capture any number of packets just by changing the first argument to pcap.loop() function 
            pcap.loop(1, jpacketHandler, "jnetpcap rocks!");
            //Close the pcap
            pcap.close();
            
        }
        else
        {
            System.out.println("Error while opening device for capture!!"+this.errbuf.toString());
            return null;
        }    
        return packetInfo;
    }
    
    public Sniffer() 
    {
        this.errbuf=new StringBuilder();
        this.interfacelist=new ArrayList<>();
        packetInfo=new ArrayList<>();
    }
    
}

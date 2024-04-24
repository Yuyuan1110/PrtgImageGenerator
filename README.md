# PRTG Image and History Generator.

## Introduction:

- This tool can download sensor chart and history from PRTG automatically.

## How To USE:

- Before you launch the tool, you need to setup the `config.properties` file, content Server IP, Port, protocol,
   username, password
- After setup the `config.properties`, then you can launch the PrtgImageGenerator.jar.
   1. Read help:
      - `java -jar PATH/TO/prthImageGenerator.jar --help`
      - ```
        [-f] [--feature]: set feature. 
        Need type parameter:
        "graphic" to download history graphic
        "history" to download history data.
        "rebuild" to rebuild settings.xml file.
        [-S] [--settingsFile]: Specify the settings file ex: -S setting.xml
        [-i] [--interval]: set interval, no interval = 0, 1 hour = 3600, ex: -i 86400
        [-s] [--sdate]: set query start date, format to "yyyy-MM-dd-HH-mm-ss"
        [-e] [--edate]: set query end date, format to "yyyy-MM-dd-HH-mm-ss"
       
        COMMAND: java -jar PRTG_Generator.jar -f [graph/history] --[start date] --[end date]
        NOTE: date format to "yyyy-MM-dd-HH-mm-ss"
        ```
   2. First time launch PrtgImageGenerator.jar will bring you to download `settings.xml`, you can specify group id if group is setup on PRTG server:
      - `java -jar PATH/TO/prthImageGenerator.jar -f graphic`
      - ```setting.log
         checking if the "config.properties" file exists?
         "config.properties OK!"
         checking if the "setting.xml" file exists?
         settings.xml not found, please check if file is exists!
         Do you want to download the setting.xml or rebuild? [D]ownload / [R]ebuild / [E]xit, default: [E].
         D
         Do you want to set the group ID or download all device? type ["ID"] or [S]kip, default [S].
         22701
         String to download devices.xml of group ID: 22701
        ```   
   3. After downloaded `settings.xml` then you can re-launch `PrtgImageGenerator.jar`, it will download chart automatically.
      - `java -jar PATH/TO/PrtgImageGenerator.jat --feature graphic -s 2023-10-01-00-00-00 -e 2024-01-01-00-00-00`
      - ```download.log
         checking if the "config.properties" file exists?
         "config.properties OK!"
         checking if the "setting.xml" file exists?
         "setting.xml OK!"
         Starting to download graphic since "2023-10-01-00-00-00" to "2024-01-01-00-00-00" ? [y/N]
         y
            
         Connecting to http://127.0.0.1:80/chart.svg?graphid=-1&id=22708&sdate=2023-10-01-00-00-00&edate=2024-01-01-00-00-00&avg=86400&graphstyling=baseFontSize%3D%2710%27%20showLegend%3D%271%27&hide=-4,0,1,2,3&width=975&height=300&username=prtgadmin&password=prtgadmin&bgcolor=%23FCFCFC
         Response Code: 200
         SVG to PNG conversion successful.
         Image downloaded successfully to: .\graph\EAP-SIM-PROXY01\Ping.png
         ```
- Setup `settings.xml`:
  1. You can setup "Hide" item in "settings.xml", remove `<channel> example </channel>` at `settings.xml` then download the chart will show the item you need.
      - ```settings.xml
         <?xml version="1.0" encoding="UTF-8" standalone="no"?>
         <devices>
             <device>
                 <deviceName>EAP-SIM-PROXY01</deviceName>
                 <deviceID>22707</deviceID>
                 <sensors>
                     <sensor>
                         <sensorName>Ping</sensorName>
                         <sensorID>22708</sensorID>
                         <channels>
                             <channel>
                                 <channelName>下線時間</channelName>
                                 <channelID>-4</channelID>
                             </channel>
                             <channel>
                                 <channelName>Ping 時間</channelName>
                                 <channelID>0</channelID>
                             </channel>
                             <channel>
                                 <channelName>最小值</channelName>
                                 <channelID>1</channelID>
                             </channel>
                             <channel>
                                 <channelName>最大值</channelName>
                                 <channelID>2</channelID>
                             </channel>
                             <channel>
                                 <channelName>封包丟失</channelName>
                                 <channelID>3</channelID>
                             </channel>
                         </channels>
                     </sensor>
                 </sensors>
         </devices>
        ```
        
- Director Structure:
   - ```dirStructure
     -PRTGGenerator
     |--PrtgImageGenrator.jar
     |--config.properties
     |--devices
     |----devices.xml
     |----'sensorIDDir'
     |------'sensorID.xml'
     |----...
     |--graph
     |----'device name'
     |------'sensor name.png'
     |----...
     ```

to be continued...

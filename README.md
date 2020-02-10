This was a personal side-project that became a group term project for Dr. John Rachlin's graduate-level Summer 2019 CS 5200 Database Management Systems course at Khoury College of Computer Sciences, Northeastern University, Boston, MA. 

The final report may be found here: https://tinyurl.com/y53tjb2w

The project is currently undergoing a redesign to: migrate to Spring Boot for the message listener module, shift to Hibernate/JPA from JDBC, improve the schema design with query performance in mind, enable PostGIS and pgRouting extensions, and possibly utilize GeoDjango for the front end. 


# RAChLiN: Real-time AIS Caching Library for Nautical vessels
The goal of this project is to collect vessel Automatic Identification System (AIS) message broadcasts using a special Very High Frequency (VHF) receiver, and store those messages in a relational database.

AIS is an internationally recognized standard for communication of navigational information between ships, shore-stations, aids-to-navigation, and search-and-rescue aircraft over Dual Channel Marine VHF bands.

Carriage of AIS equipment and broadcast of these messages are required by the International Maritime Organization and the respective maritime regulatory and enforcement organizations of individual member countries, such as the United States Coast Guard.

AIS messages are ASCII packets standardized and encoded according to National Marine Electronics Association NMEA-0183 format. Our software decodes the messages and stores the information in a database of our design. There are many possible use cases for AIS data. In this project, we demonstrate ship traffic visualization using our collected data.


![](./daisy2.gif)


### Components
Ordered by movement of data from source to end users:

[dAISY 2+](http://www.wegmatt.com/) - A dual channel AIS receiver that picks up marine broadcasts in the Boston Harbor using a TRAM 1600-HC VHF Antenna and sends the NMEA messages to the kplex multiplexer via a serial/usb connection.


[Kplex NMEA Multiplexer](http://www.stripydog.com/kplex/) - Software running on a Raspberry Pi 3B+. Acts as a NMEA server, combining incoming NMEA-0183 messages into a stream that is accessed by our remote AWS server via TCP connection. Also stores messages locally in a textlog. 

[Autossh](https://www.harding.motd.ca/autossh/) - A software tool that monitors SSH sessions and restarts dropped sessions as needed. Maintains an SSH tunnel between the AWS server and the Raspberry Pi, effectively creating a virtual private network for TCP communication. SSH tunneling is a method for transporting arbitrary networking data over an encrypted SSH connection. 

[Amazon Web Services Elastic Compute Cloud (EC2)](https://aws.amazon.com/ec2/) - A scalable virtual computer that houses the message decoder and APIs written against the database. All components operate on the same EC2 instance to keep costs within the AWS free tier, but are divided between different Docker containers. This facilitates deployment from development to production, while allowing components to be deployed at different times and with different dependencies.

[Amazon Web Services Relational Database Service (RDS)](https://aws.amazon.com/rds/) - A scalable virtual computer that houses the database. Automatically manages backups, software patching, failure detection, and recovery.

[AISmessages](https://github.com/tbsalling/aismessages) - An open source decoder library that converts AIS message streams to Java objects with human-readable attributes. The AWS server application receives a message stream via TCP connection with the Raspberry Pi and uses AISmessages to decode in real time. AISmessage objects are then used to update the database. 

[PostgreSQL](https://www.postgresql.org/) - Our database management system, operating on an RDS server. We chose PostgreSQL over MySQL due to Postgres’s support for the JSONB data type and storage of geographic information via the PostGIS extension. These features will support future enhancements to our design. 

[Apache Struts](https://struts.apache.org/) and [Apache Tomcat](http://tomcat.apache.org/) - Struts is a Java-based web framework and Tomcat is a web container. Together, these deploy web applications that use our data. In this report, we demonstrate a simple website that plots ship traffic. A Java-based API accesses our database then displays a plotted map of the Boston Harbor using JavaScript.

## Further Reading
Other Decoder Libraries: <br />
https://github.com/dma-ais <br />
https://github.com/schwehr/libais <br />

AIS Message Technical Documentation: <br />
https://gpsd.gitlab.io/gpsd/AIVDM.html <br />
https://www.navcen.uscg.gov/?pageName=AISMessages <br />

National Oceanic and Atmospheric Administration, Office for Coastal Management, U.S. Department of Commerce, and Bureau of Ocean Energy Management, U.S. Department of the Interior. “AIS Vessel Type and Group Codes Used by the Marine Cadastre Project.” AIS Vessel Type and Group Codes Used by the Marine Cadastre Project, MarineCadastre Project, NOAA Office for Coastal Management, 23 May 2018, <https://marinecadastre.gov/ais/AIS_Documents/VesselTypeCodes2018.pdf>.

U.S. Committee on the Marine Transportation System, Office of the Executive Secretariat, U.S. Department of Transportation. “Enhancing Accessibility and Useability of Automatic Identification System (AIS) Data Across the Federal Governmentand for the Benefit of Public Stakeholders”. Maritime Data Integrated Action Team, Automatic Identification System (AIS) Task Team. <https://www.cmts.gov/downloads/Accessibility_and_Usability_of_AIS_Data.pdf>.

National Maritime Electronics Association. “NMEA Collision Avoidance Through AIS.” Automatic Identification Systems, National Maritime Electronics Association, <https://www.nmea.org/Assets/nmea_collision_avoidance_through_ais.pdf>.

International Maritime Organization. “AIS Automatic Identification Systems (AIS).” AIS Automatic Identification Systems (AIS), International Maritime Organization, <http://www.imo.org/en/OurWork/Safety/Navigation/Pages/AIS.aspx>.

Isenor, Anthony W., et al. “MSARI: A Database for Large Volume Storage and Utilisation of Maritime Data.” Journal of Navigation, vol. 70, no. 2, 2017, pp. 276–290., doi:10.1017/S0373463316000540. <https://www.cambridge.org/core/journal/journal-of-navigation/article/msari-a-database-for-large-volume-storage-and-utilisation-of-maritime-data/3E7BA68539C63E2FB9E3FC6CA474737D#>.

Volpe Center, U.S. Department of Transportation. “Maritime Safety and Security Information System (MSSIS).” Volpe National Transportation Systems Center, Volpe Center, United States Department of Transportation, 3 Nov. 2017, <https://www.volpe.dot.gov/infrastructure-systems-and-technology/situational-awareness-and-logistics/maritime-safety-and>.

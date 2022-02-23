package tools;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import cartago.Artifact;
import io.vertx.ext.web.client.impl.WebClientBase;
public class WebScraping extends  Artifact  {

    public static void webScrapOutsideObservations(String year, String month, String day, String hour) {

        String temp="none";
        String humid="none";
        String pressure="none";


            WebClient client = new WebClient();
            client.setCssEnabled(false);
            client.setJavaScriptEnabled(false);

            String searchUrl = "https://www.meteociel.fr/temps-reel/obs_villes.php?code2=7475&jour2=" + day + "&mois2=" + month + "&annee2=" + year;

            try {
                HtmlPage page = client.getPage(searchUrl);

                    String temperatureXpath = String.format("//tr[td[text()[contains(.,'%s h')]]]/td[5]/div", hour);
                    String humidityXpath = String.format("//tr[td[text()[contains(.,'%s h')]]]/td[6]/div", hour);
                    String pressureXpath = String.format("//tr[td[text()[contains(.,'%s h')]]]/td[11]/div", hour);

                    HtmlDivision tempAnchor = page.getFirstByXPath(temperatureXpath);
                    HtmlDivision humidAnchor = page.getFirstByXPath(humidityXpath);
                    HtmlDivision pressAnchor = page.getFirstByXPath(pressureXpath);


                    temp = tempAnchor.asText().strip().split(" ")[0];
                    humid = humidAnchor.asText().strip().split("%")[0];
                    pressure =pressAnchor.asText().strip().split(" ")[0];
               


            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("temperature : %s humidity  : %s pressure : %s",temp,humid,pressure));
            Model m = ModelFactory.createDefaultModel();
            m.setNsPrefix("rdfs",RDFS.uri);
            m.setNsPrefix("rdf",RDFS.uri);

            m.setNsPrefix("time","http://www.w3.org/2006/time#");
            m.setNsPrefix("cdt","http://w3id.org/lindt/custom_datatypes#");
            m.setNsPrefix("xsd","http://www.w3.org/2001/XMLSchema#");
            String datasetURL = "http://localhost:3030/Sensors/";
            String sparqlEndpoint = datasetURL + "sparql";
            String sparqlUpdate = datasetURL + "update";
            String graphStore = datasetURL + "data";
          
			RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);

	        String sensor= "";
	        String geo="http://www.w3.org/2003/01/geo/wgs84_pos#";
	        String sosa="http://www.w3.org/ns/sosa/";
	        String cdt="http://w3id.org/lindt/custom_datatypes#";
	        String rdfs= RDFS.uri;
			
			
			int i=1;
			
			
            m.add(
                    m.createResource(
                            "sensor" ),
                    RDF.type,

                    m.createResource(sosa + "Sensor")

            );
            m.add(
                    m.createResource(
                            "observation_weather/" + i),
                    m.createProperty(sosa + "madeBySensor"),
                    m.createResource("sensor")
            );
            m.add(
                    m.createResource(
                            "observation_weather/" + i),
                    m.createProperty(sosa + "resultTime"),

                    m.createTypedLiteral(hour, XSDDatatype.XSDtime)


            );
              

                m.add(
                        m.createResource(
                                "observation_weather/" + i),
                        m.createProperty(sosa + "hasSimpleResult"),
                        m.createTypedLiteral(temp, cdt)


                );
                try (RDFConnection conn = RDFConnectionFactory.connect(datasetURL)) {
                    conn.load(m);

                }
			
			
			
			
			i++;
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
        }

    public static void main(String[] args) {
        SimpleDateFormat year= new SimpleDateFormat("yyyy");
        SimpleDateFormat month= new SimpleDateFormat("MM");
        SimpleDateFormat day= new SimpleDateFormat("dd");
        SimpleDateFormat hour= new SimpleDateFormat("HH");
        Date date = new Date(System.currentTimeMillis());
      System.out.println(year.format(date));
      System.out.println(Integer.parseInt(month.format(date))-1);
      int month_minceone=Integer.parseInt(month.format(date))-1;
        webScrapOutsideObservations("2022",String.valueOf(month_minceone)   ,day.format(date).toString(),hour.format(date).toString());
 
    }
}
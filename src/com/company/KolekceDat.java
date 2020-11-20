package com.company;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class KolekceDat {

    private final String OBEC = "vf:Obec";
    private final String CAST_OBCE = "vf:CastObce";

    private final String KOD_OBCE = "obi:Kod";
    private final String KOD_CAST_OBCE = "coi:Kod";

    private final String NAZEV_OBCE = "obi:Nazev";
    private final String NAZEV_CAST_OBCE = "coi:Nazev";



    private List<Obec> obce = new ArrayList<>();
    private List<CastObce> castiObce = new ArrayList<>();

    private String file;

    public KolekceDat(String file) {
        this.file = file;
    }

    public List<Obec> getObce() {

        String kod = "";
        String nazev = "";

        try {

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(file);
            inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE,false);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);



            while (eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();

                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();


                    if(startElement.getName().getLocalPart().equals(KOD_OBCE)){
                        event = eventReader.nextEvent();
                        kod = event.asCharacters().getData();
                        continue;
                    }

                    if(startElement.getName().getLocalPart().equals(NAZEV_OBCE)){
                        event = eventReader.nextEvent();
                        nazev = event.asCharacters().getData();
                        continue;
                    }


                }

                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();

                    if(endElement.getName().getLocalPart().equals(OBEC)){
                        obce.add(new Obec(kod,nazev));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return obce;

    }


    public List<CastObce> getCastiObce() {

        String kod = "";
        String nazev = "";
        String kodObce = "";

        try {

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(file);
            inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE,false);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);


            while (eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();

                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();


                    if(startElement.getName().getLocalPart().equals(KOD_CAST_OBCE)){
                        event = eventReader.nextEvent();
                        kod = event.asCharacters().getData();
                        continue;
                    }

                    if(startElement.getName().getLocalPart().equals(NAZEV_CAST_OBCE)){
                        event = eventReader.nextEvent();
                        nazev = event.asCharacters().getData();
                        continue;
                    }

                    if(startElement.getName().getLocalPart().equals(KOD_OBCE)){
                        event = eventReader.nextEvent();
                        kodObce = event.asCharacters().getData();
                        continue;
                    }
                }

                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();

                    if(endElement.getName().getLocalPart().equals(CAST_OBCE)){
                        castiObce.add(new CastObce(kod,nazev,kodObce));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return castiObce;
    }
}

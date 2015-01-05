package freemahn.com.lesson6;

import android.util.Log;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Freemahn on 05.01.2015.
 */
public class MySAXParser {


    public static ArrayList<Entry> parse(InputStream is) {
        final ArrayList<Entry> list = new ArrayList<Entry>();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();


            DefaultHandler handler = new DefaultHandler() {

                boolean bentry = false;
                boolean btitle = false;
                boolean blink = false;
                Entry current;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {

                    //System.out.println("Start Element :" + qName);

                    if (qName.equalsIgnoreCase("entry")) {
                        current = new Entry();
                        bentry = true;
                    }

                    if (qName.equalsIgnoreCase("title")&bentry) {
                        btitle = true;
                    }

                    if (qName.equalsIgnoreCase("link")&bentry) {
                        blink = true;
                        current.link = attributes.getValue("href");
                    }


                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("entry")) {
                        list.add(current);
                        bentry = false;
                    }

                   // System.out.println("End Element :" + qName);

                }

                public void characters(char ch[], int start, int length) throws SAXException {

                    /*if (bentry) {
                        Log.d("entry: ", new String(ch, start, length));
                        //bentry = false;
                    }*/

                    if (btitle) {
                        Log.d("title : ", new String(ch, start, length));
                        current.title = new String(ch, start, length);
                        btitle = false;
                    }

                    /*if (blink) {
                        Log.d("link : ", new String(ch, start, length));
                        current.link = new String(ch, start, length);
                        blink = false;
                    }*/


                }

            };

            saxParser.parse(is, handler);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}

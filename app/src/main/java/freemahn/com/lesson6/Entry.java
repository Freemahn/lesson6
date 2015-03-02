package freemahn.com.lesson6;

/**
 * Created by Freemahn on 17.10.2014.
 */
public class Entry {
    public String title;
    public String description;
    public String link;

    public Entry() {

    }

    public Entry(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }
    public Entry(String title, String link) {
        this.title = title;
        this.link = link;
    }

    @Override
    public String toString() {
        return title + " " + "\n" + link + "\n" + description;
    }
}
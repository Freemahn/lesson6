package freemahn.com.lesson6;

/**
 * Created by Freemahn on 17.10.2014.
 */
public class Entry {
    public String title;
    public String summary;
    public String link;

    //   public final String updated;
    public Entry() {

    }

    public Entry(String title, String summary, String link) {
        this.title = title;
        this.summary = summary;
        this.link = link;
        //this.updated = updated;

    }

    @Override
    public String toString() {
        return title + " " + "\n" + link + "\n" + summary;
    }
}
package beholder;

/*
 * Custom struct of encounter powers allows homebrew powers to be made.
 */
public class EncounterPower {

    public int id;
    public String power;
    public String book;
    public String pcClass;
    public int level;


    public EncounterPower (int id, String power, String book, String pcClass, int level) {
        this.id = id;
        this.power = power;
        this.book = book;
        this.pcClass = pcClass;
        this.level = level;
    }

    @Override
    public String toString() {
        String ep = "(" + this.book + ") " + this.power;
        return ep;
    }
}

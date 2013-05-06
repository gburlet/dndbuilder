public class PlayerCharacter {

    private String name;
    private int goldPieces;
    private int silverPieces;
    private int copperPieces;

    /*
     * Constructs a blank player character (PC)
     */
    public PlayerCharacter() {
        this("Character Name", 0, 0, 0);
    }

    /*
     * Alternate constructor with more PC information
     */
    public PlayerCharacter(String name, int gp, int sp, int cp) {
        this.name = name;
        this.goldPieces = gp;
        this.silverPieces = sp;
        this.copperPieces = cp;
    }
}

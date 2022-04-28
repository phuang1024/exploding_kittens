public class Card {

    private static Integer ID;

    public Card (Integer ID)
    {
        this.ID = ID;
    }
    public Card(String cardName)
    {
        switch (cardName)
        {
            case "ExplodingKitten" : ID = 1;
                break;
            case "EK" : ID = 1;
                break;
            case "Defuse" : ID = 2;
                break;
            case "Attack" : ID = 3;
                break;
            case "Skip" : ID = 4;
                break;
            case "Draw From the Bottom" : ID = 5;
                break;
            case "DFtB" : ID = 5;
                break;
            case "Nope" : ID = 6;
                break;
            case "See the Future" : ID = 7;
                break;
            case "StF" : ID = 7;
                break;
            case "Alter the Future" : ID = 8;
                break;
            case "AtF" : ID = 8;
                break;
            case "Favor" : ID = 9;
                break;
            case "Shuffle" : ID = 10;
                break;
            case "Bearded" : ID = 11;
                break;
            case "Catermelon" : ID = 12;
                break;
            case "Feral" : ID = 13;
                break;
            case "Hairy Potato" : ID = 14;
                break;
            case "Rainbow-Ralphing" : ID = 15;
                break;
            case "Tacocat" : ID = 16;
                break;

        }
    }
    public Integer getID()
    {
        return ID;
    }
}

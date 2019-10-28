package objectForStrokeBase;

public class Group {
    private int id;
    private int number;



    public Group() {
    }

    public Group(int idGroup, int numberGroup) {
        id = idGroup;
        number = numberGroup;
    }



    public Group(int numberGroup) {
        number = numberGroup;
    }

    public String toString() {
        return id + "\t" + number;
    }

    public void set(int newNumber) {
        number = newNumber;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }
}
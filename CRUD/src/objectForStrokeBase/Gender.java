package objectForStrokeBase;

public enum Gender {

    MAN ("М"),
    WOMAN ("Ж");

    private String male;

    Gender(String value) {
        this.male = value;
    }

    public String getValue() {
        return male;
    }
}
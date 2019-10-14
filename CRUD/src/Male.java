public enum Male {

    MAN ("М"),
    WOMAN ("Ж");

    private String male;

    Male(String value) {
        this.male = value;
    }

    public String getValue() {
        return male;
    }
}
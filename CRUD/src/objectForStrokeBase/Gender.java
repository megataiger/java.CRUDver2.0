package objectForStrokeBase;

public enum Gender {
    MAN("М"),
    WOMAN("Ж");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
package objectForStrokeBase;

public enum Gender {
    MAN("Муж"),
    WOMAN("Жен");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
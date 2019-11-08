package objectForStrokeBase;

import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "student")
public class Student {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "name")
    private String name;

    @Column (name = "birthday")
    private LocalDate date;

    @Enumerated (EnumType.STRING)
    @Column (name = "gender")
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "group_id")
    private Group group;



    public Student() {}

    public Student(int idStudent, String nameStudent, LocalDate birthday, Gender gender,
                   Group group) {
        id = idStudent;
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        this.group = group;
    }

    public Student(String nameStudent, LocalDate birthday, Gender gender, Group group) {
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        this.group = group;
    }



    public String toString() {
        return id + "\t" + name + "\t" + date + "\t" + gender +
                "\t" + group.getNumber();
    }

    public void setNameStudent(String newName) {
        name = newName;
    }

    public void setBirthdayStudent(LocalDate newDate) {
        date = newDate;
    }

    public void setGenderStudent(Gender newGender) {
        gender = newGender;
    }

    public void setGroupStudent(Group newGroup) {
        group = newGroup;
    }



    public String getName() {
        return name;
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonthValue() +
                "-" + date.getDayOfMonth();
    }

    public String getGender() {
        return "" + gender;
    }

    public int getGroupId() {
        return group.getId();
    }

    public int getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object obj) {
        Student student = (Student) obj;
        if(id == student.id) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        StudentDAO stud = new StudentDAO();
        GroupDAO gr = new GroupDAO();
        Group group = gr.selectGroupByNumber(441);
        Student student = new Student("dasjdhlas",
                LocalDate.of(1996, 06, 27), Gender.MAN, gr.selectGroupByNumber(441));
        stud.save(student);
    }

}


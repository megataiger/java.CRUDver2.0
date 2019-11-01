package objectForStrokeBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "`group`")
public class Group {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY )
    private int id;

    @Column (name = "number")
    private int number;

    @OneToMany (
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Student> students = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "group_teacher",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
    )
    private List<Teacher> teachers = new ArrayList<>();

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
package workWithBase.repositories;

import objectForStrokeBase.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository
        extends PagingAndSortingRepository<Student, Integer> {
    @Query(value = "SELECT s FROM Student s LEFT JOIN FETCH s.group g " +
            "WHERE LOWER(s.name) LIKE :filter " +
            "OR LOWER(s.date) LIKE :filter " +
            "OR LOWER(g.number) LIKE :filter",
    countQuery = "SELECT COUNT(s) FROM Student s LEFT JOIN Group g " +
            "ON s.group.id = g.id WHERE LOWER(s.name) LIKE :filter " +
            "OR LOWER(s.date) LIKE :filter OR LOWER(g.number) LIKE :filter")
    Page<Student> getStudents(@Param("filter") String filter,
                              Pageable pageable);

    @Query(value = "SELECT s FROM Student s WHERE s.group.id = :groupId " +
            "AND (LOWER(s.name) LIKE :filter OR LOWER(s.date) LIKE :filter)")
    Page<Student> findByGroup_Id(@Param("groupId") int groupId,
                                 @Param("filter") String filter,
                                 Pageable pageable);

    @Query(value = "SELECT COUNT(s) FROM Student s " +
            "WHERE s.group.id = :groupId")
    long getCountGroupStudents(@Param("groupId") int groupId);
}
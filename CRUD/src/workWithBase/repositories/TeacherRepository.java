package workWithBase.repositories;

import objectForStrokeBase.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository
        extends PagingAndSortingRepository<Teacher, Integer>{

    @Query("SELECT t FROM Teacher t WHERE LOWER(t.name) LIKE :filter " +
            "OR LOWER(t.date) LIKE :filter")
    Page<Teacher> getAll(@Param("filter") String filter, Pageable pageable);


    @Query("SELECT t FROM Teacher t JOIN t.groups g " +
            "WHERE g.id = :groupId AND " +
            "(LOWER(t.name) LIKE :filter OR LOWER(t.date) LIKE :filter)")
    Page<Teacher> getTeacherInGroup(@Param("groupId") int groupId,
                                     @Param("filter") String filter,
                                     Pageable pageable);

    @Query("SELECT COUNT(t) FROM Teacher t JOIN t.groups g " +
            "WHERE g.id = :groupId")
    int getCountTeacherInGroup(@Param("groupId") int groupId);

    @Query("SELECT t FROM Teacher t " +
            "WHERE (LOWER(t.name) LIKE :filter " +
            "OR LOWER(t.date) LIKE :filter) " +
            "AND t NOT IN " +
            "(SELECT t FROM Group g JOIN g.teachers t WHERE g.id = :groupId)")
    Page<Teacher> getTeacherNotInGroup(@Param("groupId") int groupId,
                                       @Param("filter") String filter,
                                       Pageable pageable);

    @Query("SELECT COUNT(t) FROM Teacher t " +
            "WHERE  t NOT IN " +
            "(SELECT t FROM Group g JOIN g.teachers t WHERE g.id = :groupId)")
    int getCountTeacherNotInGroup(@Param("groupId") int groupId);


    @Modifying
    @Query(value = "insert into group_teacher (group_id, teacher_id) " +
            "values (:groupId, :teacherId)", nativeQuery = true)
    void insertRecord(@Param("groupId") int groupId,
                      @Param("teacherId") int teacherId);

    @Modifying
    @Query(value = "delete from group_teacher " +
            "where group_id = :groupId and teacher_id = :teacherId", nativeQuery = true)
    void deleteRecord(@Param("groupId") int groupId,
                      @Param("teacherId") int teacherId);
}
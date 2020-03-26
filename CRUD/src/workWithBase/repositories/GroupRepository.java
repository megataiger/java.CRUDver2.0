package workWithBase.repositories;

import objectForStrokeBase.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository
        extends PagingAndSortingRepository<Group, Integer> {

    @Query("SELECT g FROM Group g " +
            "WHERE CAST(g.number as string) LIKE :filter")
    Page<Group> getAll(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT g FROM Group g JOIN g.teachers t " +
            "WHERE t.id = :teacherId " +
            "AND CAST(g.number as string) LIKE :filter")
    Page<Group> getGroupInTeacher(@Param("teacherId") int teacherId,
                                   @Param("filter") String filter,
                                   Pageable pageable);

    @Query("SELECT COUNT(g) FROM Group g JOIN g.teachers t " +
            "WHERE t.id = :teacherId ")
    int getCountGroupInTeacher(@Param("teacherId") int teacherId);

    @Query("SELECT g FROM Group g " +
            "WHERE CAST(g.number as string) LIKE :filter " +
            "AND g NOT IN " +
            "(SELECT g FROM Teacher t JOIN t.groups g " +
            "WHERE t.id = :teacherId)")
    Page<Group> getGroupNotInTeacher(@Param("teacherId") int teacherId,
                                     @Param("filter") String filter,
                                     Pageable pageable);

    @Query("SELECT COUNT(g) FROM Group g WHERE g NOT IN " +
            "(SELECT g FROM Teacher t JOIN t.groups g " +
            "WHERE t.id = :teacherId)")
    int getCountGroupNotInTeacher(@Param("teacherId") int teacherId);

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
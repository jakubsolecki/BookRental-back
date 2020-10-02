package pl.jsol.bookrental.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.LibraryMember;

import java.util.List;

@Repository
public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
    @Query("select lm from LibraryMember lm where lm.email like ?1")
    List<LibraryMember> findLibraryMembersWithEmail(String email);

    @Query("select lm from LibraryMember lm where lm.phone like ?1")
    List<LibraryMember> findLibraryMembersWithPhone(String phone);
}

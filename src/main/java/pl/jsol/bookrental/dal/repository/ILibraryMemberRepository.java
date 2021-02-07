package pl.jsol.bookrental.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.LibraryMember;

import java.util.List;

@Repository
public interface ILibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
    @Query("SELECT lm FROM LibraryMember lm WHERE lm.email LIKE ?1")
    List<LibraryMember> findLibraryMembersWithEmail(String email);

    @Query("SELECT lm FROM LibraryMember lm WHERE lm.phone LIKE ?1")
    List<LibraryMember> findLibraryMembersWithPhone(String phone);
}

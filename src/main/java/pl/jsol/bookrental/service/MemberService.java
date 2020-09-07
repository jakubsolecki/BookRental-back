package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.MemberRepository;
import pl.jsol.bookrental.model.Address;
import pl.jsol.bookrental.model.Member;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    //TODO string validation
    public Member addMember(String firstName,
                            String lastName,
                            String phone,
                            String email,
                            String city,
                            String street,
                            String zip) throws IllegalArgumentException {

        if (StringUtils.isAnyEmpty(firstName, lastName, phone, email, city, street, zip)) {
           throw new IllegalArgumentException("String cannot be null or empty!");
        }

        Address address = new Address(city, street, zip);
        return memberRepository.save(new Member(firstName, lastName, phone, email, address));
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Page<Member> getAllMembers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "lastName");
        return memberRepository.findAll(pageable);
    }
}

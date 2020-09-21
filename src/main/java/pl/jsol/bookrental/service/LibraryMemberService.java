package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.LibraryMemberRepository;
import pl.jsol.bookrental.model.LibraryMember;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryMemberService {

    private final LibraryMemberRepository libraryMemberRepository;

    //TODO: regex validation
    @Transactional(rollbackFor = Exception.class)
    public LibraryMember addMember(String firstName,
                                   String lastName,
                                   String phone,
                                   String email,
                                   String city,
                                   String street,
                                   String zip) throws IllegalArgumentException {

        if (StringUtils.isAnyEmpty(firstName, lastName, phone, email, city, street, zip)) {
           throw new IllegalArgumentException("String cannot be null or empty!");
        }

        LibraryMember libraryMember = LibraryMember.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .city(city)
                .street(street)
                .zip(zip)
                .build();

        return libraryMemberRepository.save(libraryMember);
    }

    @Transactional(readOnly = true)
    public Optional<LibraryMember> getMemberById(Long id) {
        return libraryMemberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<LibraryMember> getAllMembers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "lastName");
        return libraryMemberRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<LibraryMember> getLibraryMembersByExample(int page,
                                                          int size,
                                                          String firstName,
                                                          String lastName,
                                                          String phone,
                                                          String email,
                                                          String city,
                                                          String street,
                                                          String zip,
                                                          String sortStrategy,
                                                          String sortBy) {

        LibraryMember libraryMember = LibraryMember.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .city(city)
                .street(street)
                .zip(zip)
                .build();

        Pageable pageable = PageRequest.of(page, size, sortStrategy.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Example<LibraryMember> exampleOfLibraryMember = Example.of(libraryMember);

        return libraryMemberRepository.findAll(exampleOfLibraryMember, pageable);
    }

}

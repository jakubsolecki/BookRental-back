package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.ILibraryMemberRepository;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;
import pl.jsol.bookrental.model.entity.LibraryMember;

import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryMemberService {

    private final ILibraryMemberRepository ILibraryMemberRepository;
    private static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^\\w+\\.?\\w+@\\w+\\.[a-z]+\\.?[a-z]+$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_ZIP_REGEX = Pattern.compile("^\\d{2}-\\d{3}$");
    private static final Pattern VALID_PHONE_REGEX = Pattern.compile("^\\d{9}$");

    @Transactional(rollbackFor = Exception.class)
    public LibraryMember addMember(
            String firstName,
            String lastName,
            String phone,
            String email,
            String city,
            String street,
            String zip
    ) throws IllegalArgumentException {

        if (StringUtils.isAnyEmpty(firstName, lastName, phone, email, city, street, zip)) {
           throw new IllegalArgumentException("Parameters cannot be null nor empty");
        }

        verifyEmail(email);
        verifyPhone(phone);
        verifyZip(zip);

        LibraryMember libraryMember = LibraryMember.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .city(city)
                .street(street)
                .zip(zip)
                .build();

        return ILibraryMemberRepository.save(libraryMember);
    }

    @Transactional(readOnly = true)
    public LibraryMember getMemberById(Long id) {

        return ILibraryMemberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LibraryMember", id));
    }

    @Transactional(readOnly = true)
    public Page<LibraryMember> getAllMembers(int page, int size, String sort, String sortBy) {

        Sort.Direction sortDirection = (sort != null && sort.equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return ILibraryMemberRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<LibraryMember> getLibraryMembersByExample(
            int page,
            int size,
            String firstName,
            String lastName,
            String phone,
            String email,
            String city,
            String street,
            String zip,
            String sortStrategy,
            String sortBy
    ) {
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

        return ILibraryMemberRepository.findAll(exampleOfLibraryMember, pageable);
    }

    private void verifyEmail(String email) throws IllegalArgumentException {
        if(!VALID_EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email");
        }

        if(!ILibraryMemberRepository.findLibraryMembersWithEmail(email).isEmpty()) {
            throw new IllegalArgumentException("Email " + email + " is already taken");
        }
    }

    private void verifyPhone(String phone) throws IllegalArgumentException {
        if(!VALID_PHONE_REGEX.matcher(phone).matches()) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        if(!ILibraryMemberRepository.findLibraryMembersWithPhone(phone).isEmpty()) {
            throw new IllegalArgumentException("Phone " + phone + " is already assigned to the existing library member");
        }
    }

    private void verifyZip(String zip) throws IllegalArgumentException {
        if(!VALID_ZIP_REGEX.matcher(zip).matches()) {
            throw new IllegalArgumentException("Invalid zip code");
        }
    }

}

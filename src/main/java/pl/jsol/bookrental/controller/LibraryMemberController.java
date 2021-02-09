package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import pl.jsol.bookrental.model.entity.LibraryMember;
import pl.jsol.bookrental.service.LibraryMemberService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;

    @GetMapping
    public Page<LibraryMember> getAllLibraryMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "lastName") String sortBy) {

        return libraryMemberService.getAllMembers(page, size, sort, sortBy);
    }

    @GetMapping("/{id}")
    public LibraryMember getLibraryMemberById(@PathVariable Long id) {

        LibraryMember libraryMember = libraryMemberService.getMemberById(id);
        Link selfLink = linkTo(LibraryMemberController.class).slash(libraryMember.getId()).withSelfRel();

        return libraryMember.add(selfLink);
    }

    @PostMapping("/")
    public LibraryMember postLibraryMember(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam String zip) {

        LibraryMember libraryMember = libraryMemberService.addMember(firstName, lastName, phone, email, city, street, zip);
        Link selfLink = linkTo(LibraryMemberController.class).slash(libraryMember.getId()).withSelfRel();

        return libraryMember.add(selfLink);
    }
}

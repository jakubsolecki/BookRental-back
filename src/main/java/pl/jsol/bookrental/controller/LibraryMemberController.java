package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import pl.jsol.bookrental.model.entity.LibraryMember;
import pl.jsol.bookrental.service.LibraryMemberService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;
    private final PagedResourcesAssembler<LibraryMember> libraryMemberPRAssembler;

    @GetMapping
    public PagedModel<EntityModel<LibraryMember>> getAllLibraryMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "lastName") String sortBy
    ) {
        Page<LibraryMember> members = libraryMemberService.getAllMembers(page, size, sort, sortBy);

        return libraryMemberPRAssembler.toModel(members);
    }

    @GetMapping("/{id}")
    public LibraryMember getLibraryMemberById(@PathVariable Long id) {

        LibraryMember libraryMember = libraryMemberService.getMemberById(id);
        Link selfLink = linkTo(LibraryMemberController.class).slash(libraryMember.getId()).withSelfRel();

        return libraryMember.add(selfLink);
    }

    @PostMapping("/")
    public LibraryMember postLibraryMember(@RequestBody LibraryMember libraryMember) {

        LibraryMember newMember = libraryMemberService.addMember(libraryMember);
        Link selfLink = linkTo(LibraryMemberController.class).slash(newMember.getId()).withSelfRel();

        return newMember.add(selfLink);
    }

    @PutMapping("/{id}")
    public LibraryMember putLibraryMember(@RequestBody LibraryMember libraryMember) {
        return null; // TODO
    }
}

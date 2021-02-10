package pl.jsol.bookrental.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jsol.bookrental.model.entity.LibraryMember;
import pl.jsol.bookrental.service.LibraryMemberService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;
    private final PagedResourcesAssembler<LibraryMember> libraryMemberPRAssembler;
    private final ObjectMapper objectMapper;

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

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public LibraryMember patchLibraryMember(@PathVariable Long id, @RequestBody JsonPatch patch) {

        try {
            LibraryMember oldMember = libraryMemberService.getMemberById(id);
            LibraryMember updatedMember = patchLibraryMember(oldMember, patch);
            updatedMember = libraryMemberService.updateLibraryMember(updatedMember);
            Link selfLink = linkTo(LibraryMemberController.class).slash(updatedMember.getId()).withSelfRel();

            return updatedMember.add(selfLink);
        } catch (JsonProcessingException | JsonPatchException jex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, jex.getMessage());
        }

    }

    private LibraryMember patchLibraryMember(
            LibraryMember memberToPatch,
            JsonPatch patch
    ) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(memberToPatch, JsonNode.class));
        return objectMapper.treeToValue(patched, LibraryMember.class);
    }
}

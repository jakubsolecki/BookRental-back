package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.MemberRepository;
import pl.jsol.bookrental.model.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;
}

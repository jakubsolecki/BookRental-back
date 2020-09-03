package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;
}

package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.domain.Member;
import org.example.dto.request.MemberCreateRequest;
import org.example.dto.response.MemberResponse;
import org.example.exception.BusinessRuleException;
import org.example.exception.NotFoundException;
import org.example.mapper.DtoMapper;
import org.example.repository.MemberRepository;
import org.example.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final DtoMapper mapper;

    @Override
    public MemberResponse create(MemberCreateRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessRuleException("Email already exists");
        }
        Member m = Member.builder()
                .fullName(request.fullName())
                .email(request.email())
                .build();
        return mapper.toMemberResponse(memberRepository.save(m));
    }

    @Override
    public MemberResponse getById(Long id) {
        Member m = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found: " + id));
        return mapper.toMemberResponse(m);
    }

    @Override
    public List<MemberResponse> getAll() {
        return memberRepository.findAll().stream().map(mapper::toMemberResponse).toList();
    }
}

package org.example.service;

import org.example.dto.request.MemberCreateRequest;
import org.example.exception.BusinessRuleException;
import org.example.mapper.DtoMapper;
import org.example.repository.MemberRepository;
import org.example.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MemberServiceTest {
    @Test
    void create_shouldThrow_whenEmailExists() {
        MemberRepository repo = mock(MemberRepository.class);
        DtoMapper mapper = new DtoMapper();
        MemberService service = new MemberServiceImpl(repo, mapper);

        when(repo.existsByEmail("a@b.com")).thenReturn(true);

        assertThrows(BusinessRuleException.class,
                () -> service.create(new MemberCreateRequest("A", "a@b.com")));
    }
}

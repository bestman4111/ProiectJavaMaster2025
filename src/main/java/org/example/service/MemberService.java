package org.example.service;

import org.example.dto.request.MemberCreateRequest;
import org.example.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberResponse create(MemberCreateRequest request);
    MemberResponse getById(Long id);
    List<MemberResponse> getAll();
}

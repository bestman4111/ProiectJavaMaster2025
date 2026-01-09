package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.MemberCreateRequest;
import org.example.dto.response.MemberResponse;
import org.example.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "Create member")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(@Valid @RequestBody MemberCreateRequest request) {
        return memberService.create(request);
    }

    @Operation(summary = "Get member by id")
    @GetMapping("/{id}")
    public MemberResponse get(@PathVariable Long id) {
        return memberService.getById(id);
    }

    @Operation(summary = "List members")
    @GetMapping
    public List<MemberResponse> list() {
        return memberService.getAll();
    }
}

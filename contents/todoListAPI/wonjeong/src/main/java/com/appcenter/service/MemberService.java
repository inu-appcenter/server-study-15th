package com.appcenter.service;

import com.appcenter.data.dto.request.MemberRequestDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO getMember(Long id) throws Exception;

    MemberResponseDTO savedMember(MemberRequestDTO memberRequestDTO);

    MemberResponseDTO updateMember(Long id, MemberRequestDTO memberRequestDTO) throws Exception;

    void deleteMember(Long id) throws Exception;
}

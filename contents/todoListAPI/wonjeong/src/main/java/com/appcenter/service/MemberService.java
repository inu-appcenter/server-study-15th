package com.appcenter.service;

import com.appcenter.data.dto.MemberDTO;
import com.appcenter.data.dto.request.MemberRequestDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO getMember(Long id) throws Exception;

    MemberResponseDTO savedMember(MemberDTO memberDTO);

    MemberResponseDTO changeMemberinfo(MemberRequestDTO memberRequestDTO) throws Exception;

    void deleteMember(Long id) throws Exception;
}

package com.appcenter.service;

import com.appcenter.data.dto.MemberDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO getMember(Long id) throws Exception;

    MemberResponseDTO savedMember(MemberDTO memberDTO);

    MemberResponseDTO changeMemberinfo(Long id, String name) throws Exception;

    void deleteMember(Long id) throws Exception;
}

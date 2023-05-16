package com.appcenter.service;

import com.appcenter.data.dto.MemberDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO getMember(Long number);

    MemberResponseDTO savedMember(MemberDTO memberDTO);

    MemberResponseDTO changeMemberinfo(Long number, String name) throws Exception;

    void deleteMember(Long number) throws Exception;
}

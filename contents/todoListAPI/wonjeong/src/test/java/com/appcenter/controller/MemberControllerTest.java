package com.appcenter.controller;

import com.appcenter.data.dto.response.MemberResponseDTO;
import com.appcenter.security.SecurityConfiguration;
import com.appcenter.service.MemberService;
import com.appcenter.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class,
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class)
            })
@MockBean(JpaMetamodelMappingContext.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean()
    MemberServiceImpl memberService;

    @Test
    @DisplayName("MockTest MVC를 통한 데이터 가져오기 테스트")
    @WithMockUser
    void getMemberTest() throws Exception {
        given(memberService.getMember(1L)).willReturn(
                new MemberResponseDTO(1L, "테스트", "1234", "test@test.com"));
        mockMvc.perform(
                get("/member/3"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(memberService).getMember(1L);
    }
}

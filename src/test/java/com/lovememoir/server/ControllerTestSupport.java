package com.lovememoir.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovememoir.server.api.controller.avatar.AvatarApiController;
import com.lovememoir.server.api.controller.avatar.AvatarQueryApiController;
import com.lovememoir.server.api.controller.diary.DiaryApiController;
import com.lovememoir.server.api.controller.diary.DiaryQueryApiController;
import com.lovememoir.server.api.controller.diarypage.DiaryPageApiController;
import com.lovememoir.server.api.controller.diarypage.DiaryPageQueryApiController;
import com.lovememoir.server.api.controller.member.MemberApiController;
import com.lovememoir.server.api.service.diary.DiaryQueryService;
import com.lovememoir.server.api.service.diary.DiaryService;
import com.lovememoir.server.api.service.diarypage.DiaryPageQueryService;
import com.lovememoir.server.api.service.diarypage.DiaryPageService;
import com.lovememoir.server.api.service.member.MemberService;
import com.lovememoir.server.common.interceptor.query.ApiQueryCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Controller Test Code 컨텍스트 캐싱
 *
 * @author dong82
 */
@WithMockUser
@WebMvcTest(controllers = {
    MemberApiController.class,
    AvatarApiController.class, AvatarQueryApiController.class,
    DiaryApiController.class, DiaryQueryApiController.class,
    DiaryPageApiController.class, DiaryPageQueryApiController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ApiQueryCounter apiQueryCounter;

    @MockBean
    protected DiaryService diaryService;

    @MockBean
    protected DiaryQueryService diaryQueryService;

    @MockBean
    protected DiaryPageService diaryPageService;

    @MockBean
    protected DiaryPageQueryService diaryPageQueryService;

    @MockBean
    protected MemberService memberService;

}

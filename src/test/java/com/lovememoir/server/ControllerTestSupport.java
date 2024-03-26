package com.lovememoir.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovememoir.server.api.service.diary.DiaryQueryService;
import com.lovememoir.server.api.service.diary.DiaryService;
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
@WebMvcTest
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected DiaryService diaryService;

    @MockBean
    protected DiaryQueryService diaryQueryService;
}

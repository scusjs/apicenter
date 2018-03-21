package me.liexing.apicenter.ones;

import me.liexing.apicenter.general.interceptor.ApiSkInterceptor;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OnesTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockHttpSession mockHttpSession;
    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(ApiSkInterceptor.SESSION_SK_KEY, "http://localhost");

    }

    @Test
    public void ones() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/one").accept(MediaType.APPLICATION_JSON).session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONObject result = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(result.get("code"), 0);
        Assert.assertNotNull(result.getJSONObject("data").get("forward"));
    }
}

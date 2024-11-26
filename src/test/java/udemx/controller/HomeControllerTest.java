package udemx.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class HomeControllerTest {

    private final HomeController homeController = mock(HomeController.class);
    private MockMvc mockMvc;

    @Test
    public void test_get_root_returns_home_view() {
        String viewName = homeController.home();
        assertEquals("home", viewName);
    }

    @Test
    public void test_controller_handles_root_mapping() throws NoSuchMethodException {
        GetMapping mapping = homeController.getClass().getDeclaredMethod("home").getAnnotation(GetMapping.class);
        assertEquals("/", mapping.value()[0]);
    }

    @Test
    public void test_get_mapping_annotation_present() throws NoSuchMethodException {

        Method homeMethod = homeController.getClass().getDeclaredMethod("home");
        assertTrue(homeMethod.isAnnotationPresent(GetMapping.class));
    }

    @Test
    public void test_home_method_return_value() {

        String result = homeController.home();
        assertNotNull(result);
        assertEquals("home", result);
    }

    @Test
    public void test_controller_annotation_present() {
        Class<?> controllerClass = HomeController.class;
        assertTrue(controllerClass.isAnnotationPresent(Controller.class));
    }


    @Test
    public void test_query_params_handling() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        mockMvc.perform(get("/?param=value"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void test_invalid_http_method() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        mockMvc.perform(post("/"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void test_home_endpoint_returns_home_string() {

        String result = homeController.home();
        assertEquals("home", result);
    }

    @Test
    public void test_character_encoding_handling() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        mockMvc.perform(get("/").characterEncoding("UTF-16"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

}

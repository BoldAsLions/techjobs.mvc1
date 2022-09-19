package org.launchcode.techjobs.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

/**
 * Created by LaunchCode
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TestTaskFour {

    @Autowired
    MockMvc mockMvc;

    /*
     * Make sure the search results page loads
     * */
    @Test
    public void testSearchResultsLoad() throws Exception {
        mockMvc.perform(post("/search/results")
            .param("searchType", "all")
            .param("searchTerm", "asdf"))
            .andExpect(status().isOk());
    }

    /*
    * Searching by location for "new york" should return 1 result
    * */
    @Test
    public void testFirstSearchTestCase() throws Exception {
        mockMvc.perform(post("/search/results")
                .param("searchType", "location")
                .param("searchTerm", "new york"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][2]").doesNotExist());
    }

    /*
     * Searching by location for "chicago" should return no results
     * */
    @Test
    public void testSecondSearchTestCase() throws Exception {
        mockMvc.perform(post("/search/results")
                .param("searchType", "location")
                .param("searchTerm", "chicago"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]").doesNotExist());
    }

    /*
     * Searching by employer for "equifax" should return 1 result
     * */
    @Test
    public void testThirdSearchTestCase() throws Exception {
        mockMvc.perform(post("/search/results")
                .param("searchType", "employer")
                .param("searchTerm", "equifax"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][2]").doesNotExist());
    }

    /*
     * Searching by all fields for "ruby" should return 4 results
     * */
    @Test
    public void testFourthSearchTestCase() throws Exception {
        mockMvc.perform(post("/search/results")
                .param("searchType", "all")
                .param("searchTerm", "ruby"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][2]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][3]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][4]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][5]").doesNotExist());
    }

    /*
     * Searching by skill for "ruby" should return 3 results
     * */
    @Test
    public void testFifthSearchTestCase() throws Exception {
        mockMvc.perform(post("/search/results")
                .param("searchType", "skill")
                .param("searchTerm", "ruby"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][2]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][3]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][4]").doesNotExist());
    }

}

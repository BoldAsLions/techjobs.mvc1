package org.launchcode.techjobs.mvc;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

/**
 * Created by LaunchCode
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TestTaskTwo {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testJobListingLoads () throws Exception {
        mockMvc.perform(get("/list/jobs?column=coreCompetency&value=Ruby"))
                .andExpect(status().isOk());
    }

    /*
    * Looks for a <table> element anywhere within the job listing page
    * */
    @Test
    public void testJobListingUsesTable () throws Exception {
        mockMvc.perform(get("/list/jobs?column=coreCompetency&value=Ruby"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table").exists());
    }

    /*
    * Looks for the class 'table-listing' on the table in the job listing page
    * */
    @Test
    public void testJobListingUsesCSSClass () throws Exception {
        mockMvc.perform(get("/list/jobs?column=coreCompetency&value=Ruby"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')]").exists());
    }

    /*
    * Looking at the first job table only, verify that all job fields are listed
    * */
    @Test
    public void testJobListingDisplaysAllJobFields () throws Exception {
        mockMvc.perform(get("/list/jobs?column=coreCompetency&value=Ruby"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]/tr/td[contains(text(), '3')]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]/tr/td[contains(text(), 'Junior Web Developer')]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]/tr/td[contains(text(), 'Cozy')]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]/tr/td[contains(text(), 'Portland')]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]/tr/td[contains(text(), 'Web - Front End')]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]/tr/td[contains(text(), 'Ruby')]").exists());
    }

    /*
    * Verifies that there are exactly 3 Ruby jobs listed
    * */
    @Test
    public void testJobListingDisplaysAllRelevantJobs () throws Exception {
        mockMvc.perform(get("/list/jobs?column=coreCompetency&value=Ruby"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][1]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][2]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][3]").exists())
                .andExpect(xpath("//table[contains(@class, 'job-listing')][4]").doesNotExist());
    }

    /*
    * Checks for the "View All" link on /list
    * */
    @Test
    public void testViewAllLinkExists() throws Exception {
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(xpath("//table/tr/td//a[starts-with(@href, '/list/jobs?column=all')]").exists());
    }

}

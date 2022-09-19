package org.launchcode.techjobs.mvc;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.launchcode.techjobs.mvc.controllers.SearchController;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by LaunchCode
 */
public class TestTaskThree {

    @Tested
    SearchController searchController;

    /*
    * Check that displaySearchMethod exists and has the correct parameter types
    * */
    @Test
    public void testDisplaySearchResultsMethodExists() throws ClassNotFoundException {
        Class searchControllerClass = Class.forName("org.launchcode.techjobs.mvc.controllers.SearchController");
        Method displaySearchResultsMethod = null;

        try {
            displaySearchResultsMethod = searchControllerClass.getMethod("displaySearchResults", Model.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            fail("SearchController does not have a displaySearchResults method");
        }
    }

    /*
    * Check that displaySearchMethod has the correct request mapping annotation and value
    * */
    @Test
    public void testDisplaySearchResultsUsesCorrectAnnotation() throws ClassNotFoundException, NoSuchMethodException {
        Class searchControllerClass = Class.forName("org.launchcode.techjobs.mvc.controllers.SearchController");
        Method displaySearchResultsMethod = searchControllerClass.getMethod("displaySearchResults", Model.class, String.class, String.class);
        PostMapping annotation = displaySearchResultsMethod.getDeclaredAnnotation(PostMapping.class);
        assertNotNull(annotation, "displaySearchResults should use @PostMapping");
        assertEquals(annotation.value()[0], "results", "displaySearchResults should be at the route /search/results");
    }

    /*
    * Check the parameters to displaySearchMethod have the correct annotations
    * */
    @Test
    public void testDisplaySearchResultsHasCorrectParameterAnnotations() throws ClassNotFoundException, NoSuchMethodException {
        Class searchControllerClass = Class.forName("org.launchcode.techjobs.mvc.controllers.SearchController");
        Method displaySearchResultsMethod = searchControllerClass.getMethod("displaySearchResults", Model.class, String.class, String.class);
        Annotation[][] parameterAnnotations = displaySearchResultsMethod.getParameterAnnotations();
        assertEquals(parameterAnnotations[0].length, 0, "The first parameter to displaySearchResults should have no annotations");
        assertEquals(parameterAnnotations[1].length, 1, "The second parameter to displaySearchResults should have 1 annotation");
        assertEquals(RequestParam.class, parameterAnnotations[1][0].annotationType(), "The second parameter to displaySearchResults should have the @RequestParam annotation");
        assertEquals(parameterAnnotations[2].length, 1, "The third parameter to displaySearchResults should have 1 annotation");
        assertEquals(RequestParam.class, parameterAnnotations[2][0].annotationType(), "The third parameter to displaySearchResults should have the @RequestParam annotation");
    }

    /*
    * Check that displaySearchResults has the correct parameter names, ensure parameter binding occurs
    * */
    @Test
    public void testDisplaySearchResultsHasCorrectParameterNames() throws ClassNotFoundException, NoSuchMethodException {
        Class searchControllerClass = Class.forName("org.launchcode.techjobs.mvc.controllers.SearchController");
        Method displaySearchResultsMethod = searchControllerClass.getMethod("displaySearchResults", Model.class, String.class, String.class);
        Parameter[] parameters = displaySearchResultsMethod.getParameters();
        assertEquals("searchType", parameters[1].getName());
        assertEquals("searchTerm", parameters[2].getName());
    }

    /*
    * Checks that displaySearchResults calls JobData.findAll when apppropriate
    * */
    @Test
    public void testDisplaySearchResultsCallsFindAll(@Mocked JobData jobData) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class searchControllerClass = Class.forName("org.launchcode.techjobs.mvc.controllers.SearchController");
        Method displaySearchResultsMethod = searchControllerClass.getMethod("displaySearchResults", Model.class, String.class, String.class);

        new Expectations() {{
            JobData.findAll();
        }};

        Model model = new ExtendedModelMap();
        displaySearchResultsMethod.invoke(searchController, model, "all", "all");
    }

    /*
     * Checks that displaySearchResults calls JobData.findByColumnAndValue when apppropriate
     * */
    @Test
    public void testDisplaySearchResultsCallsFindByColumnAndValue(@Mocked JobData jobData) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class searchControllerClass = Class.forName("org.launchcode.techjobs.mvc.controllers.SearchController");
        Method displaySearchResultsMethod = searchControllerClass.getMethod("displaySearchResults", Model.class, String.class, String.class);

        new Expectations() {{
            JobData.findByColumnAndValue("skill", "ruby");
        }};

        Model model = new ExtendedModelMap();
        displaySearchResultsMethod.invoke(searchController, model, "skill", "ruby");
    }

    /*
    * Checks that displaySearchResults sets the necessary model attributes
    * */
    @Test
    public void testDisplaySearchResultsSetsModelAtts() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class searchControllerClass = Class.forName("org.launchcode.techjobs.mvc.controllers.SearchController");
        Method displaySearchResultsMethod = searchControllerClass.getMethod("displaySearchResults", Model.class, String.class, String.class);
        Model model = new ExtendedModelMap();
        displaySearchResultsMethod.invoke(searchController, model, "skill", "ruby");
        assertNotNull(model.getAttribute("jobs"));
        assertNotNull(model.getAttribute("columns"));
    }

}

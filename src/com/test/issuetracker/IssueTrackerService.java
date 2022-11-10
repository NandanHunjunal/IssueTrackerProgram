package com.test.issuetracker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Service class which provides service method to read the json response and prints them with the details
 */
public class IssueTrackerService {

    /**
     * Reads the issues from the given repository and prints the issue in the below format
     *  title_of_the_issue -> characters in the first message of each issue
     *
     * @param issues_url
     * @throws IOException
     * @throws ParseException
     */
    public void printIssues(String issues_url) throws IOException, ParseException {
        URL url = new URL(issues_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Getting the response code
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            printJSONResponse(url);
        }
    }

    /**
     * Reads the JSON response and then renders through the response to print the details required
     *
     * @param url
     * @throws IOException
     * @throws ParseException
     */
    private void printJSONResponse(URL url) throws IOException, ParseException {
        String inline = "";
        Scanner scanner = new Scanner(url.openStream());

        //Write all the JSON data into a string using a scanner
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }

        //Close the scanner
        scanner.close();

        //Using the JSON simple library parse the string into a json object
        JSONParser parse = new JSONParser();
        JSONArray issues = (JSONArray) parse.parse(inline);

        System.out.println("============= ISSUES ===============");
        printTheIssues(issues);
        System.out.println("============= End ===============");
    }

    /**
     * Iterates the given issues and prints them in the below format
     *  title_of_the_issue -> characters in the first message of each issue
     *
     * @param issues
     */
    private void printTheIssues(JSONArray issues) {
        issues.iterator().forEachRemaining(element -> {
            if (element instanceof JSONObject) {
                JSONObject issue = (JSONObject) element;
                //Get the open issue and print them
                System.out.println(issue.get("title") + "  ->  " + getFirstMessageCharacterCount((String)issue.get("body")) + " characters");
            }
        });
    }

    /**
     * Gets the legth of the first issue message
     *
     * @param body
     * @return
     */
    private int getFirstMessageCharacterCount(String body) {
        if(body != null) {
            return body.split("\n")[0].length();
        }
        return 0;
    }

}

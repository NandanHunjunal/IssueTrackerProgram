package com.test.issuetracker;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Lists the open issues from the provided GitHub repository
 */
public class IssueTracker {

    public static final String GITHUB_REPOSITORY = "https://api.github.com/repos/planetlabs/staccato/issues?state=open";

    public static void main(String[] args) {

        IssueTrackerService issueTrackerService = new IssueTrackerService();
        try {
            issueTrackerService.printIssues(GITHUB_REPOSITORY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

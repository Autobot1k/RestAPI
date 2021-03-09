package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPITest extends TestBase {
    TestBase testBase;
    String serviceUrl;
    String apiUrl;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeMethod
    public void setUp() throws IOException {
        testBase = new TestBase();
        serviceUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("serviceURL");
        //https://reqres.in/api/users/

        url = serviceUrl + apiUrl;

    }

    @Test(priority = 1)
    public void getAPITestWithoutHeaders() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        //a. Status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status code: " + statusCode);

        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //b. JSON String
        String responseString =  EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJason= new JSONObject(responseString);
        System.out.println("Response JSON from API: " + responseJason);

        //per_page:
        String perPageValue = TestUtil.getValueByJPath(responseJason, "/per_page");
        System.out.println("value \"per_page\" is: " + perPageValue);

        Assert.assertEquals(Integer.parseInt(perPageValue), 6);

        //total:
        String totalValue = TestUtil.getValueByJPath(responseJason, "/total");
        System.out.println("value \"total\" is: " + totalValue);

        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        // get the value from JSON ARRAY
        String lastName = TestUtil.getValueByJPath(responseJason, "/data[1]/last_name");
        String firstName = TestUtil.getValueByJPath(responseJason, "/data[1]/first_name");
        String id = TestUtil.getValueByJPath(responseJason, "/data[1]/id");
        String avatar = TestUtil.getValueByJPath(responseJason, "/data[1]/avatar");

        System.out.println(lastName);
        System.out.println(firstName);
        System.out.println(id);
        System.out.println(avatar);

        //c. All headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for(Header header: headersArray){
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers Array: " + allHeaders);


    }

    @Test(priority = 2)
    public void getAPITestWithHeaders() throws IOException {
        restClient = new RestClient();

        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Connection", "keep-alive");
//        headerMap.put("username", "test");
//        headerMap.put("password", "test123");
//        headerMap.put("Auth Token", "12345");

        closeableHttpResponse = restClient.get(url, headerMap);

        //a. Status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status code: " + statusCode);

        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //b. JSON String
        String responseString =  EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJason= new JSONObject(responseString);
        System.out.println("Response JSON from API: " + responseJason);

        //per_page:
        String perPageValue = TestUtil.getValueByJPath(responseJason, "/per_page");
        System.out.println("value \"per_page\" is: " + perPageValue);

        Assert.assertEquals(Integer.parseInt(perPageValue), 6);

        //total:
        String totalValue = TestUtil.getValueByJPath(responseJason, "/total");
        System.out.println("value \"total\" is: " + totalValue);

        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        // get the value from JSON ARRAY
        String lastName = TestUtil.getValueByJPath(responseJason, "/data[1]/last_name");
        String firstName = TestUtil.getValueByJPath(responseJason, "/data[1]/first_name");
        String id = TestUtil.getValueByJPath(responseJason, "/data[1]/id");
        String avatar = TestUtil.getValueByJPath(responseJason, "/data[1]/avatar");

        System.out.println(lastName);
        System.out.println(firstName);
        System.out.println(id);
        System.out.println(avatar);

        //c. All headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for(Header header: headersArray){
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers Array: " + allHeaders);


    }
}

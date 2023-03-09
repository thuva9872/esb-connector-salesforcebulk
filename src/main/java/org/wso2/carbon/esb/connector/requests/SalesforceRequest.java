package org.wso2.carbon.esb.connector.requests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.CreateQueryJobPayload;
import org.wso2.carbon.esb.connector.pojo.GetAllJobResponse;
import org.wso2.carbon.esb.connector.pojo.GetAllQueryJobResponse;
import org.wso2.carbon.esb.connector.pojo.JobInfo;
import org.wso2.carbon.esb.connector.pojo.CreateJobPayload;
import org.wso2.carbon.esb.connector.pojo.QueryJobInfo;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.utils.FileUtils;
import org.wso2.carbon.esb.connector.utils.HttpMethod;
import org.wso2.carbon.esb.connector.utils.RequestConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

import java.util.HashMap;

public class SalesforceRequest {
    private Log log = LogFactory.getLog(this.getClass());
    private SalesforceConfig salesforceConfig;
    private static final String ACCESS_TOKEN = "access_token";

    public SalesforceRequest(SalesforceConfig salesforceConfig) {
        this.salesforceConfig = salesforceConfig;
    }

    public JobInfo createJob(CreateJobPayload createJobPayload) throws ResponseParsingException, SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        String jobInfoJson = createJobPayload.toJson();
        RestRequest restRequest =
                new RestRequest(HttpMethod.POST, SalesforceUtils.getCreateJobUrl(salesforceConfig), jobInfoJson, headers);
        RestResponse restResponse = sendRequest(restRequest);
        return JobInfo.fromJson(restResponse.getResponse());
    }

    public QueryJobInfo createQueryJob(CreateQueryJobPayload createQueryJobPayload) throws ResponseParsingException, SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        String queryJobInfoJson = createQueryJobPayload.toJson();
        System.out.println(queryJobInfoJson);
        RestRequest restRequest =
                new RestRequest(HttpMethod.POST, SalesforceUtils.getCreateQueryJobUrl(salesforceConfig), queryJobInfoJson, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return QueryJobInfo.fromJson(restResponse.getResponse());
    }

    public void uploadJobData(String jobId, String filePath) throws SalesforceConnectionException, InvalidConfigurationException {
        FileUtils.verifyFile(filePath);
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.TEXT_CSV);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.PUT, SalesforceUtils.getUploadJobDataUrl(salesforceConfig, jobId), null, headers);
        restRequest.setGetBodyFromFile(true);
        restRequest.setInputFilePath(filePath);
        sendRequest(restRequest);
    }

//    public void closeJob(String jobId) throws SalesforceConnectionException {
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
//        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
//        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
//        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
//        String jobStateJson = "{\"state\":\"" + BulkJobState.UploadComplete + "\"}";
//        RestRequest restRequest =
//                new RestRequest(HttpMethod.PATCH, SalesforceUtils.getCloseJobUrl(salesforceConfig, jobId), jobStateJson, headers);
//
//        sendRequest(restRequest);
//    }
//
//    public void abortJob(String jobId) throws SalesforceConnectionException {
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
//        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
//        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
//        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
//        String jobStateJson = "{\"state\":\"" + BulkJobState.Aborted + "\"}";
//        RestRequest restRequest =
//                new RestRequest(HttpMethod.PATCH, SalesforceUtils.getAbortJobUrl(salesforceConfig, jobId), jobStateJson, headers);
//
//        sendRequest(restRequest);
//    }
//
//    public void abortQueryJob(String queryJobId) throws SalesforceConnectionException {
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
//        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
//        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
//        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
//        String jobStateJson = "{\"state\":\"" + BulkQueryJobState.Aborted + "\"}";
//        RestRequest restRequest =
//                new RestRequest(HttpMethod.PATCH, SalesforceUtils.getAbortQueryJobUrl(salesforceConfig, queryJobId), jobStateJson, headers);
//
//        sendRequest(restRequest);
//    }

    public void deleteJob(String jobId) throws SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.DELETE, SalesforceUtils.getDeleteJobUrl(salesforceConfig, jobId), null, headers);

        sendRequest(restRequest);
    }

    public void deleteQueryJob(String queryJobId) throws SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.DELETE, SalesforceUtils.getDeleteQueryJobUrl(salesforceConfig, queryJobId), null, headers);

        sendRequest(restRequest);
    }

    public GetAllJobResponse getAllJobInfo() throws ResponseParsingException, SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetAllJobInfoUrl(salesforceConfig), null, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return GetAllJobResponse.fromJson(restResponse.getResponse());
    }

    public GetAllQueryJobResponse getAllQueryJobInfo() throws ResponseParsingException, SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetAllQueryJobInfoUrl(salesforceConfig), null, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return GetAllQueryJobResponse.fromJson(restResponse.getResponse());
    }

    public JobInfo getJobInfo(String jobId) throws ResponseParsingException, SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobInfoUrl(salesforceConfig, jobId), null, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return JobInfo.fromJson(restResponse.getResponse());
    }

    public QueryJobInfo getQueryJobInfo(String queryJobId) throws ResponseParsingException, SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetQueryJobInfoUrl(salesforceConfig, queryJobId), null, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return QueryJobInfo.fromJson(restResponse.getResponse());
    }

    public void getQueryJobResults(String queryJobId, String filePath, Integer maxRecords, String locator)
            throws SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetQueryJobResultUrl(salesforceConfig, queryJobId, maxRecords, locator), null, headers);

        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public void getJobFailedResults(String jobId, String filePath) throws SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobFailedResultsUrl(salesforceConfig, jobId), null, headers);

        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public void getJobSuccessfulResults(String jobId, String filePath) throws SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobSuccessfulResultsUrl(salesforceConfig, jobId), null, headers);
        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public void getJobUnprocessedResults(String jobId, String filePath) throws SalesforceConnectionException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobUnprocessedResultsUrl(salesforceConfig, jobId), null, headers);

        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public void renewAccessToken() throws SalesforceConnectionException {
        RestRequest restRequest =
                new RestRequest(HttpMethod.POST, SalesforceUtils.getSFTokenUrl(salesforceConfig), null, null);

        RestResponse restResponse = restRequest.send();
        String responseStr = restResponse.getResponse();
        try {
            JSONObject jsonObject = new JSONObject(responseStr);
            salesforceConfig.setAccessToken(jsonObject.getString(ACCESS_TOKEN));
            log.info("Access token renewed successfully.");
        } catch (JSONException e) {
            throw new SalesforceConnectionException("Error while parsing token response to json. " +
                    "Access token renewal process failed with exception: ", e);
        }
    }

    private RestResponse sendRequest(RestRequest restRequest) throws SalesforceConnectionException {
        RestResponse restResponse = restRequest.send();
        if (restResponse.isError()) {
            if (restResponse.getStatusCode() == 401) {
                log.info("Access token expired. Renewing access token");
                renewAccessToken();
                restRequest.getHeaders().put(RequestConstants.HTTP_HEADER_AUTHORIZATION,
                        RequestConstants.BEARER + salesforceConfig.getAccessToken());
                log.info("Retrying request with renewed access token.");
                return restRequest.send();
            } else {
                throw new SalesforceConnectionException("Error while sending request to salesforce. Status code: " +
                        restResponse.getStatusCode() +
                        ". Error message : " + restResponse.getErrorMessage() +
                        ". Error detail : " + restResponse.getErrorDetails());
            }
        }
        return restResponse;
    }



}

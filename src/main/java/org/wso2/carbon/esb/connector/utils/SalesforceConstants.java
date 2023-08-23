/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.esb.connector.utils;

public class SalesforceConstants {
    public static final String ACCESS_TOKEN = "sf_bulk_accessToken";
    public static final String REFRESH_TOKEN = "sf_bulk_refreshToken";
    public static final String CLIENT_ID = "sf_bulk_clientId";
    public static final String CLIENT_SECRET = "sf_bulk_clientSecret";
    public static final String SF_OAUTH_CONFIG_NAME = "sf_bulk_config_name";
    public static final String INSTANCE_URL = "sf_bulk_instanceUrl";
    public static final String SF_API_VERSION = "v57.0";
    public static final String SF_API_JOBS_INGEST_RELATIVE_PATH = "/services/data/"
            + SF_API_VERSION + "/jobs/ingest/";
    public static final String SF_API_JOBS_QUERY_RELATIVE_PATH = "/services/data/"
            + SF_API_VERSION + "/jobs/query/";
    public static final String SF_TOKEN_RELATIVE_PATH = "/services/oauth2/token";
    public static final String SF_API_JOBS_BATCHES = "/batches/";
    public static final String SF_API_JOB_FAILED_RESULTS_RELATIVE_PATH = "/failedResults/";
    public static final String SF_API_JOB_SUCCESSFUL_RESULTS_RELATIVE_PATH = "/successfulResults/";
    public static final String SF_API_JOB_UNPROCESSED_RESULTS_RELATIVE_PATH = "/unprocessedrecords/";
    public static final String SF_API_JOBS_QUERY_RESULTS_RELATIVE_PATH = "/results";
    public static final String ASSIGNMENT_RULE_ID = "assignmentRuleId";
    public static final String COLUMN_DELIMITER = "columnDelimiter";
    public static final String EXTERNAL_ID_FIELD_NAME = "externalIdFieldName";
    public static final String LINE_ENDING = "lineEnding";
    public static final String OPERATION = "operation";
    public static final String OBJECT = "object";
    public static final String JOB_ID = "jobId";
    public static final String QUERY_JOB_ID = "queryJobId";
    public static final String QUERY = "query";
    public static final String LOCATOR = "locator";
    public static final String MAX_RECORDS = "maxRecords";
    public static final String CLOSE_JOB_URL = "sf_bulk_closeJobUrl";
    public static final String ABORT_JOB_URL = "sf_bulk_abortJobUrl";
    public static final String ABORT_QUERY_JOB_URL = "sf_bulk_abortQueryJobUrl";
    public static final String CREATE_JOB_URL = "sf_bulk_createJobUrl";
    public static final String CREATE_QUERY_JOB_URL = "sf_bulk_createQueryJobUrl";
    public static final String DELETE_JOB_URL = "sf_bulk_deleteJobUrl";
    public static final String DELETE_QUERY_JOB_URL = "sf_bulk_deleteQueryJobUrl";
    public static final String ALL_JOB_INFO_URL = "sf_bulk_allJobInfoUrl";
    public static final String ALL_QUERY_JOB_INFO_URL = "sf_bulk_allQueryJobInfoUrl";
    public static final String GET_FAILED_RESULTS_URL = "sf_bulk_getFailedResultsUrl";
    public static final String JOB_INFO_URL = "sf_bulk_jobInfoUrl";
    public static final String QUERY_JOB_INFO_URL = "sf_bulk_queryJobInfoUrl";
    public static final String GET_QUERY_JOB_RESULTS_URL = "sf_bulk_getQueryJobResultsUrl";
    public static final String GET_SUCCESSFUL_RESULTS_URL = "sf_bulk_getSuccessfulResultsUrl";
    public static final String GET_UNPROCESSED_RESULTS_URL = "sf_bulk_getUnprocessedResultsUrl";
    public static final String UPLOAD_JOB_DATA_URL = "sf_bulk_uploadJobUrl";
    public static final String TOKEN_URL = "sf_bulk_tokenUrl";
    public static final String PAYLOAD = "sf_bulk_payload";
    public static final String INPUT_DATA = "inputData";
    public static final String IS_PK_CHUNKING_ENABLED = "isPkChunkingEnabled";
    public static final String JOB_TYPE = "jobType";
    public static final String QUERY_LOCATOR = "queryLocator";
}

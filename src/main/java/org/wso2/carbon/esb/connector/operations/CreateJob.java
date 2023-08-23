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
package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.CreateJobPayload;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class CreateJob extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {
        try {
            log.debug("Creating salesforce job");
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);
            CreateJobPayload createJobPayload = getCreateJobPayload(messageContext);
            String createJobUrl = SalesforceUtils.getCreateJobUrl(salesforceConfig);
            messageContext.setProperty(SalesforceConstants.CREATE_JOB_URL, createJobUrl);
            String payload = createJobPayload.toJson();
            messageContext.setProperty(SalesforceConstants.PAYLOAD, payload);
        } catch (Exception e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            SalesforceUtils.generateErrorOutput(messageContext, e);
            if (!(e instanceof SalesforceConnectionException)) {
                handleException(e.getMessage(), e, messageContext);
            } else {
                log.error(e.getMessage(), e);
            }
        }
    }

    private CreateJobPayload getCreateJobPayload(MessageContext messageContext) throws InvalidConfigurationException {
        String object = (String) getParameter(messageContext, SalesforceConstants.OBJECT);
        String operation = (String) getParameter(messageContext, SalesforceConstants.OPERATION);
        String externalIdFieldName = (String) getParameter(messageContext, SalesforceConstants.EXTERNAL_ID_FIELD_NAME);
        String lineEnding = (String) getParameter(messageContext, SalesforceConstants.LINE_ENDING);
        String columnDelimiter = (String) getParameter(messageContext, SalesforceConstants.COLUMN_DELIMITER);
        String assignmentRuleId = (String) getParameter(messageContext, SalesforceConstants.ASSIGNMENT_RULE_ID);

        CreateJobPayload createJobPayload =
                new CreateJobPayload(SalesforceUtils.getBulkJobOperationTypeEnum(operation),
                        object, externalIdFieldName);
        createJobPayload.setLineEnding(SalesforceUtils.getLineEndingEnum(lineEnding));
        createJobPayload.setColumnDelimiter(SalesforceUtils.getColumnDelimiterEnum(columnDelimiter));
        createJobPayload.setAssignmentRuleId(assignmentRuleId);
        return createJobPayload;
    }
}

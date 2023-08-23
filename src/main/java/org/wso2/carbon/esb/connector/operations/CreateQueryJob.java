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
import org.wso2.carbon.esb.connector.pojo.CreateQueryJobPayload;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class CreateQueryJob extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {
        try {
            log.debug("Creating salesforce query job");
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);
            CreateQueryJobPayload createQueryJobPayload = getCreateQueryJobPayload(messageContext);
            String payload = createQueryJobPayload.toJson();
            String createQueryJobUrl = SalesforceUtils.getCreateQueryJobUrl(salesforceConfig);
            messageContext.setProperty(SalesforceConstants.CREATE_QUERY_JOB_URL, createQueryJobUrl);
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

    private CreateQueryJobPayload getCreateQueryJobPayload(MessageContext messageContext)
            throws InvalidConfigurationException {
        String operation = (String) getParameter(messageContext, SalesforceConstants.OPERATION);
        String query = (String) getParameter(messageContext, SalesforceConstants.QUERY);
        String lineEnding = (String) getParameter(messageContext, SalesforceConstants.LINE_ENDING);
        String columnDelimiter = (String) getParameter(messageContext, SalesforceConstants.COLUMN_DELIMITER);

        CreateQueryJobPayload createQueryJobPayload =
                new CreateQueryJobPayload(SalesforceUtils.getBulkQueryJobOperationTypeEnum(operation).getOperationType()
                        , query);
        createQueryJobPayload.setLineEnding(SalesforceUtils.getLineEndingEnum(lineEnding));
        createQueryJobPayload.setColumnDelimiter(SalesforceUtils.getColumnDelimiterEnum(columnDelimiter));
        return createQueryJobPayload;
    }
}

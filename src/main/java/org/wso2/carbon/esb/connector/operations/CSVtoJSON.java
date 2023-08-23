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

import org.apache.axiom.om.OMElement;
import org.apache.axis2.builder.Builder;
import org.apache.axis2.builder.BuilderUtil;
import org.apache.axis2.transport.TransportUtils;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CSVtoJSON extends AbstractConnector {

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public void connect(MessageContext messageContext) {

        try {
            String output = messageContext.getEnvelope().getBody().getFirstElement().getText();
            String jsonOutput = "{}";
            if (!output.isEmpty()) {
                jsonOutput = SalesforceUtils.csvToJson(output);
            }
            org.apache.axis2.context.MessageContext axis2MsgCtx = ((org.apache.synapse.core.axis2.
                    Axis2MessageContext) messageContext).getAxis2MessageContext();
            Builder builder = null;
            builder = BuilderUtil.getBuilderFromSelector(APPLICATION_JSON, axis2MsgCtx);

            InputStream jsonStream = new ByteArrayInputStream(jsonOutput.getBytes());
            OMElement documentElement = builder.processDocument(jsonStream, APPLICATION_JSON, axis2MsgCtx);
            documentElement.toString();
            messageContext.setEnvelope(TransportUtils.createSOAPEnvelope(documentElement));
        } catch (Exception e) {
            log.error("Error occurred while building json payload", e);
        }
    }
}

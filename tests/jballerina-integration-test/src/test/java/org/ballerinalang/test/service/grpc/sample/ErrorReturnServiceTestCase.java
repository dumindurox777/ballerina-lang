/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.ballerinalang.test.service.grpc.sample;

import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.test.util.BCompileUtil;
import org.ballerinalang.test.util.BRunUtil;
import org.ballerinalang.test.util.CompileResult;
import org.ballerinalang.test.util.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test class for gRPC unary service which returns error.
 */
@Test(groups = "grpc-test")
public class ErrorReturnServiceTestCase extends GrpcBaseTest {

    private CompileResult result;

    @BeforeClass
    private void setup() throws Exception {
        TestUtils.prepareBalo(this);
        Path balFilePath = Paths.get("src", "test", "resources", "grpc", "src", "clients",
                "13_unary_client_with_error_return.bal");
        result = BCompileUtil.compile(balFilePath.toAbsolutePath().toString());
    }

    @Test
    public void testBlockingErrorResponse() {
        final String serverMsg = "Error from Connector: {ballerina/grpc}InternalError - error Testing message=Details";

        BValue[] responses = BRunUtil.invoke(result, "testErrorResponse", new Object[]{"WSO2"});
        Assert.assertEquals(responses.length, 1);
        Assert.assertTrue(responses[0] instanceof BString);
        Assert.assertEquals(responses[0].stringValue(), serverMsg);
    }
}

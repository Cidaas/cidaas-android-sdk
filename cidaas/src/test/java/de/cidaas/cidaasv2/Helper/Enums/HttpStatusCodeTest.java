package de.cidaas.cidaasv2.Helper.Enums;

import junit.framework.Assert;

import org.junit.Test;


public class HttpStatusCodeTest {
    @Test
    public void check() {


        Assert.assertEquals(200, HttpStatusCode.OK);
        Assert.assertEquals(204, HttpStatusCode.NO_CONTENT);
        Assert.assertEquals(307, HttpStatusCode.TEMPORARY_REDIRECT);
        Assert.assertEquals(400, HttpStatusCode.BAD_REQUEST);
        Assert.assertEquals(401, HttpStatusCode.UNAUTHORIZED);
        Assert.assertEquals(403, HttpStatusCode.FORBIDDEN);
        Assert.assertEquals(404, HttpStatusCode.NOT_FOUND);
        Assert.assertEquals(405, HttpStatusCode.METHOD_NOT_ALLOWED);
        Assert.assertEquals(409, HttpStatusCode.CONFLICT);
        Assert.assertEquals(413, HttpStatusCode.PAYLOAD_TOO_LARGE);
        Assert.assertEquals(415, HttpStatusCode.UNSUPPORTED_MEDIA_TYPE);
        Assert.assertEquals(417, HttpStatusCode.EXPECTATION_FAILED);
        Assert.assertEquals(499, HttpStatusCode.CANCEL_REQUEST);
        Assert.assertEquals(500, HttpStatusCode.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(501, HttpStatusCode.DEFAULT);
        Assert.assertEquals(502, HttpStatusCode.BAD_GATEWAY);
        Assert.assertEquals(503, HttpStatusCode.SERVICE_UNAVAILABLE);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
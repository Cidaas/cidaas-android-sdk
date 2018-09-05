package com.example.cidaasv2.Service.Repository.ChangePassword

import android.content.Context
import com.example.cidaasv2.Service.CidaassdkService
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import static org.mockito.Mockito.*

class ChangePasswordServiceTest  {
    @Mock
    CidaassdkService service
    @Mock
    ObjectMapper objectMapper
    @Mock
    Context context
    @Mock
    ChangePasswordService shared
    @InjectMocks
    ChangePasswordService changePasswordService

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test get Shared"() {
        when:
        ChangePasswordService result = ChangePasswordService.getShared(null)

        then:
        result == new ChangePasswordService(null)
    }

    def "test change Password"() {
        given:
        when(service.getInstance()).thenReturn(null)

        when:
        changePasswordService.changePassword(new ChangePasswordRequestEntity(access_token: "access_token"), "baseurl", null)

        then:
        false//todo - validate something
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
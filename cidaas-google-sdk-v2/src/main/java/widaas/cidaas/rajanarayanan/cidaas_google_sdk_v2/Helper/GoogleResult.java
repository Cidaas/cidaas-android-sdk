package widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Helper;

import com.example.cidaasv2.Helper.Extension.WebAuthError;

public interface GoogleResult<T> {
        public void success(T result);
        public void failure(WebAuthError error);

}

    package com.polytechnic.astra.ac.id.ippperformance;
    import java.io.IOException;
    import okhttp3.Interceptor;
    import okhttp3.Request;
    import okhttp3.Response;

    public class RetryInterceptor implements Interceptor {
        private final int maxRetries;
        private int retryCount = 0;

        public RetryInterceptor(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = null;
            boolean responseOK = false;
            while (!responseOK && retryCount < maxRetries) {
                try {
                    response = chain.proceed(request);
                    responseOK = response.isSuccessful();
                } catch (Exception e) {
                    retryCount++;
                    if (retryCount >= maxRetries) {
                        throw e;
                    }
                }
            }
            return response;
        }
    }

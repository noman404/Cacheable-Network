package al.com.cacheable.network.listener;


import al.com.cacheable.network.enums.NetworkStatus;

/**
 * Created by Al Noman on 5/18/2017.
 */

public interface NetworkResponse {

    void onSuccessResponse(
            String response,
            Object reference);

    void onErrorResponse(
            String endpoint,
            String error,
            Object reference);

    void onAuthError(
            NetworkStatus networkStatus,
            Object reference);
}

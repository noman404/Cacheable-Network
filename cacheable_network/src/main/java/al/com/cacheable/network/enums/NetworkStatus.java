package al.com.cacheable.network.enums;

/**
 * Created by Al Noman on 5/18/2017.
 */

public enum NetworkStatus {

    OK(200), /**
     * SUCCESS
     */
    UNAUTHORIZED(401), /**
     * INVALID USER
     */
    FORBIDDEN(403);
    /**
     * INVALID ACCESS TOKEN
     */

    int status;

    NetworkStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

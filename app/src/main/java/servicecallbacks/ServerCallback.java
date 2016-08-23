package servicecallbacks;


public interface ServerCallback {
    void onGetResponse(String serverResponse);
    void onPostResponse(String serverResponse);
}
